package service;

import Base.BaseTest;
import com.hlt.dto.ImageHolder;
import com.hlt.dto.ProductExecution;
import com.hlt.entity.Product;
import com.hlt.entity.ProductCategory;
import com.hlt.entity.Shop;
import com.hlt.enums.ProductStateEnum;
import com.hlt.exceptions.ShopOperationException;
import com.hlt.service.ProductService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;

    //测试批量上传详情图片
    @Test
    public void testAddProduct() throws ShopOperationException, FileNotFoundException{
        //创建shopId为1且productCategoryId为1的商品实例并给其成员变量赋值
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(1L);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("测试批量商品图片1");
        product.setProductDesc("测试商品描述1");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        //创建缩略图文件流
        File thumbnailFile = new File("C:/Users/胡栗涛/Desktop/resource/image/xiaohuangren1.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(),is);
        //创建两个商品详情图文件流并将他们添加到详情图列表中
        File productImg1 = new File("C:/Users/胡栗涛/Desktop/resource/image/xiaohuangren1.jpg");
        InputStream is1 = new FileInputStream(productImg1);
        File productImg2 = new File("C:/Users/胡栗涛/Desktop/resource/image/xiaohuangren2.jpg");
        InputStream is2 = new FileInputStream(productImg2);

        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(productImg1.getName(),is1));
        productImgList.add(new ImageHolder(productImg2.getName(),is2));
        //添加商品并验证
        ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());
    }

    //测试更新商品信息以及商品图片信息
    @Test
    public void testModifyProduct() throws ShopOperationException,FileNotFoundException{
        //创建shopId为1且productCategoryId为1的商品实例并给其成员变量赋值
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(1L);
        product.setProductId(1L);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("测试更新的商品");
        product.setProductDesc("测试更新的商品描述");
        //创建缩略图文件流
        File thumbnailFile = new File("C:/Users/胡栗涛/Desktop/resource/image/xiaohuangren5.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);
        //创建两个商品详情图文件流并将他们添加到详情图列表中
        File productImg1 = new File("C:/Users/胡栗涛/Desktop/resource/image/xiaohuangren2.jpg");
        InputStream is1 = new FileInputStream(productImg1);
        File productImg2 = new File("C:/Users/胡栗涛/Desktop/resource/image/66.jpg");
        InputStream is2 = new FileInputStream(productImg2);
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(productImg1.getName(),is1));
        productImgList.add(new ImageHolder(productImg1.getName(),is2));
        //添加商品并验证
        ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());
    }

}