package com.hlt.service.impl;

import com.hlt.dao.ShopDao;
import com.hlt.dto.ShopExecution;
import com.hlt.entity.Shop;
import com.hlt.enums.ShopStateEnum;
import com.hlt.service.ShopService;
import com.hlt.util.ImageUtil;
import com.hlt.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.io.InputStream;
import java.util.Date;

@Service
@Transactional
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) {
        if (shop == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            //给店铺赋值并加入到数据库
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            int effectedNum = shopDao.insertShop(shop);
            if(effectedNum <= 0){
                throw new RuntimeException("店铺创建失败");           //RuntimeException事务会终止并回滚
//                throw new ShopOperationException("店铺创建失败");
            }else {
                if(shopImgInputStream !=null){
                    //存储图片
                    try {
                        addShopImg(shop, shopImgInputStream,fileName);
                    }catch (Exception e){
                        throw new RuntimeException("addShopImg error:" + e.getMessage());
//                        throw new ShopOperationException("addShopImg error:" + e.getMessage());
                    }
                    //更新店铺的图片地址
                    effectedNum = shopDao.updateShop(shop);
                    if(effectedNum <=0){
                        throw new RuntimeException("更新图片地址失败");
//                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }
            
        }catch (Exception e){
            throw new RuntimeException("addShop error:" + e.getMessage());
//            throw new ShopOperationException("addShop error:" + e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    private void addShopImg(Shop shop, InputStream shopImgInputStream,String fileName) {
        //获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream,fileName,dest);
        shop.setShopImg(shopImgAddr);
    }

}