package service;

import Base.BaseTest;
import com.hlt.entity.Area;
import com.hlt.service.AreaService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class AreaServiceTest extends BaseTest {

    @Autowired
    private AreaService areaService;
    @Test
    public void testGetAreaList(){

        List<Area> areaList = areaService.getAreaList();
        assertEquals("西苑",areaList.get(0).getAreaName());
    }
}