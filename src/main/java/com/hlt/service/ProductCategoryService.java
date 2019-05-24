package com.hlt.service;

import com.hlt.dto.ProductCategoryExecution;
import com.hlt.entity.ProductCategory;
import com.hlt.exceptions.ProductCategoryOperationException;

import java.util.List;

public interface ProductCategoryService {

    //查询指定某个店铺下的所有商品类别
    List<ProductCategory> getProductCategoryList(long shopId);
    //批量新增商品类别
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;
    //删除指定商品类别：将此类别下的商品里的类别id置为空，再删除掉商品类别
    ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId)throws ProductCategoryOperationException;
}