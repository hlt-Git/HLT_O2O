package dao;

import Base.BaseTest;
import com.hlt.dao.ShopDao;
import com.hlt.entity.Area;
import com.hlt.entity.PersonInfo;
import com.hlt.entity.Shop;
import com.hlt.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import static junit.framework.TestCase.assertEquals;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;
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