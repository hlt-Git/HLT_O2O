package dao;

import Base.BaseTest;
import com.hlt.dao.ProductImgDao;
import com.hlt.entity.ProductImg;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)   //按照方法的名字升序顺序去执行,字典顺序
public class ProductImgDaoTest extends BaseTest {
    @Autowired
    private ProductImgDao productImgDao;

    //测试批量添加商品详情图片
    @Test
    public void testABatchInsertProductImg() throws Exception{
        //productId为1的商品里添加两个详情图片记录
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片3");
        productImg1.setImgDesc("测试图片3");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(1L);

        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片4");
        productImg2.setImgDesc("测试图片4");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(1L);
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2,effectedNum);
    }

    //测试查询
    @Test
    public void testBQueryProductImgList(){
        //检查productId为1的商品是否有且仅有两张商品详情图片
        List<ProductImg> productImgList = productImgDao.queryProductImgList(1L);
        assertEquals(2,productImgList.size());
    }

    //测试删除
    @Test
    public void testCDeleteProductImgByProductId() throws Exception {
        //删除商品Id为1的商品详情图片记录
        long productId = 1;
        int effectedNum = productImgDao.deleteProductImgByProductId(productId);
        assertEquals(2, effectedNum);
    }
}