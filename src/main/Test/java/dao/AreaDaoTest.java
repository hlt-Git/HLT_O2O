package dao;

import Base.BaseTest;
import com.hlt.dao.AreaDao;
import com.hlt.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class AreaDaoTest extends BaseTest {
    @Autowired
    private AreaDao areaDao;

//    测试获取全部区域信息
    @Test
    public void testQueryArea(){
        List<Area> areaList = areaDao.queryArea();

        for (Area area : areaList) {
            System.out.println(area);
        }
        assertEquals(2,areaList.size());
    }
}