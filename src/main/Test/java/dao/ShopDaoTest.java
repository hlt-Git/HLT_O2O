package dao;

import Base.BaseTest;
import com.hlt.dao.ShopDao;
import com.hlt.entity.Area;
import com.hlt.entity.PersonInfo;
import com.hlt.entity.Shop;
import com.hlt.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;


public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;
//    测试店铺列表和数量
    @Test
    public void testQueryShopListAndCount(){
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
        int count = shopDao.queryShopCount(shopCondition);
        for (Shop shop : shopList) {
            System.out.println(shop);
        }
        System.out.println("店铺列表大小：" + shopList.size());
        System.out.println("店铺总数：" + count);
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(3L);
        shopCondition.setShopCategory(sc);
        shopList = shopDao.queryShopList(shopCondition, 0, 2);
        System.out.println("新店铺列表大小：" + shopList.size());
        count = shopDao.queryShopCount(shopCondition);
        System.out.println("新店铺总数：" + count);
    }

//    测试通过Id查找店铺
    @Test
    public void testQueryByShopId(){
        long shopId = 1;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println("areaId:" + shop.getArea().getAreald());
        System.out.println("areaName:" + shop.getArea().getAreaName());
    }

//    测试添加店铺
    @Test
    public void testInsertShop(){
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();

        owner.setUserId(1L);
        area.setAreald(2);
        shopCategory.setShopCategoryId(1L);

//        Shop shop = new Shop(null,"特使的店铺","店铺的描述","店铺的地址",123,"店铺logo",2,new Date(),new Date(),1,"审核中",area,owner,shopCategory);

        shop.setShopName("特使的店铺");
        shop.setShopDesc("店铺的描述");
        shop.setShopAddr("店铺的地址");
        shop.setPhone("店铺的电话");
        shop.setShopImg("店铺logo");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        shop.setArea(area);
        shop.setOwner(owner);
        shop.setShopCategory(shopCategory);
        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1,effectedNum);
    }

//    测试更新店铺
    @Test
    public void testUpdateShop(){
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopDesc("这是一个神奇的店铺");
        shop.setShopAddr("这是一个神秘的地址");
        shop.setLastEditTime(new Date());
        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1,effectedNum);
    }
}