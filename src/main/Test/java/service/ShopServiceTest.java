package service;

import Base.BaseTest;
import com.hlt.dto.ImageHolder;
import com.hlt.dto.ShopExecution;
import com.hlt.entity.Area;
import com.hlt.entity.PersonInfo;
import com.hlt.entity.Shop;
import com.hlt.entity.ShopCategory;
import com.hlt.enums.ShopStateEnum;
import com.hlt.exceptions.ShopOperationException;
import com.hlt.service.ShopService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void testGetShopList(){
        Shop shopCondition = new Shop();
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(3L);
        shopCondition.setShopCategory(sc);
        ShopExecution se = shopService.getShopList(shopCondition, 1, 2);
        System.out.println("店铺列表数为：" + se.getShopList().size());
        System.out.println("店铺总数为：" + se.getCount());
    }

    @Test
    public void testModifyShop() throws ShopOperationException,FileNotFoundException{
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopName("修改后的店铺名称");
        File shopImg = new File("C:/Users/胡栗涛/Desktop/resource/image/logo2.jpg");
        InputStream is = new FileInputStream(shopImg);
        ImageHolder imageHolder = new ImageHolder("logo2.jpg", is);
        ShopExecution shopExecution = shopService.modifyShop(shop,imageHolder);
        System.out.println("新的图片地址：" + shopExecution.getShop().getShopImg());
    }

    @Test
    public void testAddShop() throws FileNotFoundException {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreald(2);
        shopCategory.setShopCategoryId(1L);
        shop.setArea(area);
        shop.setOwner(owner);
        shop.setShopCategory(shopCategory);
        shop.setShopName("添加的店铺3");
        shop.setShopDesc("店铺3的描述");
        shop.setShopAddr("店铺3的地址");
        shop.setPhone("店铺3的电话");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg = new File("C:/Users/胡栗涛/Desktop/resource/image/xiaohuangren1.jpg");
        InputStream is = new FileInputStream(shopImg);
        ImageHolder imageHolder = new ImageHolder(shopImg.getName(), is);
        ShopExecution se = shopService.addShop(shop,imageHolder);
        assertEquals(ShopStateEnum.CHECK.getState(),se.getState());
    }
}