package com.hlt.service;

import com.hlt.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
//    查询店铺类别
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);

}