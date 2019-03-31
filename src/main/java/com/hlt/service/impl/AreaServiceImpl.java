package com.hlt.service.impl;

import java.util.List;
import com.hlt.dao.AreaDao;
import com.hlt.entity.Area;
import com.hlt.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;
//    列出区域列表
    @Override
    public List<Area> getAreaList() {
        return areaDao.queryArea();
    }
}
