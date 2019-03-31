package com.hlt.service;

import com.hlt.dto.ShopExecution;
import com.hlt.entity.Shop;
import com.hlt.exceptions.ShopOperationException;

import java.io.File;
import java.io.InputStream;
import java.security.PublicKey;

public interface ShopService {

    public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);    //根据shopCondition分页返回相应店铺列表

    Shop getByShopId(long shopId);              //通过Id获取店铺信息

    ShopExecution modifyShop(Shop shop,InputStream shopImgInputStream,String fileName) throws ShopOperationException;    //更新店铺信息

    ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName)throws ShopOperationException;       //添加店铺

}