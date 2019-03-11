package com.hlt.dao;

import com.hlt.entity.Shop;

public interface ShopDao {

    int insertShop(Shop shop);      //增加店铺，返回1代表插入成功，返回-1代表插入失败

    int updateShop(Shop shop);      //更新店铺的信息


}
