package com.hlt.service;

import com.hlt.dto.ShopExecution;
import com.hlt.entity.Shop;
import com.hlt.exceptions.ShopOperationException;

import java.io.File;
import java.io.InputStream;

public interface ShopService {

    ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName)throws ShopOperationException;     //添加店铺

}
