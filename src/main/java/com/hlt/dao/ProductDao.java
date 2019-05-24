package com.hlt.dao;

import com.hlt.entity.Product;

public interface ProductDao {
    //插入商品
    int insertProduct(Product product);
    //查询商品信息
    Product queryProductById(long productId);
    //更新商品信息
    int updateProduct(Product product);
}