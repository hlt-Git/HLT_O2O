package com.hlt.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hlt.dto.ImageHolder;
import com.hlt.dto.ProductExecution;
import com.hlt.entity.Product;
import com.hlt.entity.ProductCategory;
import com.hlt.entity.Shop;
import com.hlt.enums.ProductStateEnum;
import com.hlt.exceptions.ProductOperationException;
import com.hlt.service.ProductCategoryService;
import com.hlt.service.ProductService;
import com.hlt.util.CodeUtil;
import com.hlt.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    //支持上传商品详情图的最大数量
    private static final int IMAGEMAXCOUNT = 6;

    //批量添加商品详情图片
    @RequestMapping(value = "/addproduct",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addProduct(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        //验证码校验
        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        //接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        ImageHolder thumbnail = null;

        String productStr = HttpServletRequestUtil.getString(request, "productStr");

        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        //文件上传组件,获取文件流
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            //若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
            if (multipartResolver.isMultipart(request)){
                //======重构后的代码======
                thumbnail = handleImage( request, thumbnail, productImgList);
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg","上传图片不能为空");
                return modelMap;
            }
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }
        try {
            //尝试获取前端传过来的表单string流并将其转换成Product实体类
            product = mapper.readValue(productStr,Product.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }
        //若Product信息，缩略图以及详情图列表为非空，则开始进行商品添加操作
        if (product != null && thumbnail != null && productImgList.size() > 0){
            try {
                //从session中获取当前店铺的Id并赋值给product,减少对前端数据的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                //执行添加操作
                ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",pe.getStateInfo());
                }
            }catch (ProductOperationException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
                return modelMap;
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入商品信息");
        }
        return modelMap;
    }

    private ImageHolder handleImage(HttpServletRequest request, ImageHolder thumbnail, List<ImageHolder> productImgList) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //取出缩略图并构建ImageHolder对象
        CommonsMultipartFile thumbnail1File = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
        if (thumbnail1File != null) {
            thumbnail = new ImageHolder(thumbnail1File.getOriginalFilename(), thumbnail1File.getInputStream());
        }
        //取出详情图列表并构建List<ImageHolder>列表对象，最多支持六张图片上传
        for (int i = 0; i < IMAGEMAXCOUNT; i++) {
            CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
            if (productImgFile != null) {
                //若取出的第i个详情图片文件流不为空，则将其加入详情图列表
                ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
                productImgList.add(productImg);
            } else {
                //若取出的第i个详情图片文件流为空，则终止循环
                break;
            }
        }
        return thumbnail;
    }

    //通过商品id获取商品信息
    @RequestMapping(value = "/getproductbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getProductById(@RequestParam Long productId){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (productId > -1) {
            Product product = productService.getProductById(productId);
            //获取该店铺下的商品类别列表
            List<ProductCategory> productCategoryList = productCategoryService
                    .getProductCategoryList(product.getShop().getShopId());
            modelMap.put("product", product);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty productId");
        }
        return modelMap;
    }

    //更新商品信息以及商品图片信息
    //商品编辑
    @RequestMapping(value = "/modifyproduct",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> modifyProduct(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //是商品编辑时候调用还是上下架操作的时候调用
        //若为前者则进行验证码判断，后者则跳过验证码判断
        boolean statusChange = HttpServletRequestUtil.getBoolean(request,
                "statusChange");
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        //接收前端参数数据
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
        try {
            if (multipartResolver.isMultipart(request)) {
                //======重构后的代码======
                thumbnail = handleImage( request, thumbnail, productImgList);
            }
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }

        try {
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            //尝试获取前端传过来的表单string流并将其转换成Product实体类
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if (product != null) {
            try {
                //从session中获取当前店铺的Id并赋值给product，减少对前端数据的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                //开始进行商品信息变更操作
                ProductExecution pe = productService.modifyProduct(product,
                        thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

}