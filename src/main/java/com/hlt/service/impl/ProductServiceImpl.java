package com.hlt.service.impl;

import com.hlt.dao.ProductDao;
import com.hlt.dao.ProductImgDao;
import com.hlt.dto.ImageHolder;
import com.hlt.dto.ProductExecution;
import com.hlt.entity.Product;
import com.hlt.entity.ProductImg;
import com.hlt.enums.ProductStateEnum;
import com.hlt.exceptions.ProductOperationException;
import com.hlt.service.ProductService;
import com.hlt.util.ImageUtil;
import com.hlt.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    //添加商品信息以及图片处理
    //1.处理缩略图，获取缩略图相对路径并赋值给product
    //2.往tb_product写入商品信息，获取productId
    //3.结合productId批量处理商品详情图
    //4.将商品详情图列表批量插入tb_product_img中
    @Override
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException {
        //空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null){
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            //默认为上架的状态
            product.setEnableStatus(1);
            //若商品缩略图不为空则添加
            if(thumbnail != null){
                addThumbnail(product,thumbnail);
            }
            try {
                //创建商品信息
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <= 0){
                    throw new ProductOperationException("创建商品失败");
                }
            }catch (Exception e){
                throw new ProductOperationException("创建商品失败:" + e.toString());
            }
            //若商品详情图不为空则添加
            if (productImgList != null && productImgList.size() > 0){
                addProductImgList(product,productImgList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS);
        }else {
            //传参为空则返回空值错误信息
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    //通过id查询
    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }

    //更新商品信息以及商品图片信息
    @Override
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException {
       if (product != null && product.getShop() !=null && product.getShop().getShopId() != null){
           product.setLastEditTime(new Date());
           //若商品缩略图不为空且原有缩略图不为空则删除原有缩略图并添加
           if (thumbnail != null){
               Product tempProduct = productDao.queryProductById(product.getProductId());
               if (tempProduct.getImgAddr() != null){
                   ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
               }
               addThumbnail(product,thumbnail);
           }
           //如果有新存入的商品详情图，则将原先的删除，并添加新的图片
           if (productImgHolderList != null && productImgHolderList.size() > 0){
               deleteProductImgList(product.getProductId());
               addProductImgList(product,productImgHolderList);
           }
           try {
               //更新商品信息
               int effectedNum = productDao.updateProduct(product);
               if (effectedNum <= 0){
                   throw new ProductOperationException("更新商品信息失败");
               }
               return new ProductExecution(ProductStateEnum.SUCCESS,product);
           }catch (Exception e){
               throw new ProductOperationException("更新商品信息失败:" + e.toString());
           }
       }else{
           return new ProductExecution(ProductStateEnum.EMPTY);
       }
    }

    //添加缩略图
    private void addThumbnail(Product product,ImageHolder thumbnail){
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail,dest);
        product.setImgAddr(thumbnailAddr);
    }
    //批量添加图片
    private void addProductImgList(Product product,List<ImageHolder> productImgHolderList){
        //获取图片存储路径，这里直接存放到相应店铺的文件夹底下
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        //遍历图片一次去处理，并添加进productImg实体类里
        for (ImageHolder productImgHolder : productImgHolderList ) {
            String imgAddr = ImageUtil.generateNormalImg(productImgHolder,dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        //如果确实是有图片需要添加的，就执行批量添加操作
        if (productImgList.size() > 0){
            try {
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectedNum <= 0){
                    throw new ProductOperationException("创建商品详情图片失败!");
                }
            }catch (Exception e){
                throw new ProductOperationException("创建商品详情图片失败" + e.toString());
            }
        }
    }
    //删除某个商品下的所有详情图
    private void deleteProductImgList(long productId) {
        //获取原来的图片
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        //干掉原来的图片
        for (ProductImg productImg : productImgList) {
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        //删除数据库里原有图片的信息
        productImgDao.deleteProductImgByProductId(productId);
    }

}