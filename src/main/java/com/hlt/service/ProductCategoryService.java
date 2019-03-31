package com.hlt.service;

import com.hlt.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    //查询指定某个店铺下的所有商品类别
    List<ProductCategory> getProductCategoryList(long shopId);

}
