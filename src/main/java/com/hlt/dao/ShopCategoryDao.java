package com.hlt.dao;

import com.hlt.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface ShopCategoryDao {
//    查询店铺类别
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition")ShopCategory shopCategoryCondition);

}