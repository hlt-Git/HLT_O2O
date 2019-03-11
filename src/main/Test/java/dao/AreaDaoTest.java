package dao;

import Base.BaseTest;
import com.hlt.dao.AreaDao;
import com.hlt.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import static junit.framework.TestCase.assertEquals;

public class AreaDaoTest extends BaseTest {
    @Autowired
    private AreaDao areaDao;

    @Test
    public void testQueryArea(){
        List<Area> areaList = areaDao.queryArea();

        for (Area area : areaList) {
            System.out.println(area);
        }
        assertEquals(2,areaList.size());
    }
}