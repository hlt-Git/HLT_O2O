package com.hlt.service.impl;

import com.hlt.dao.ShopDao;
import com.hlt.dto.ImageHolder;
import com.hlt.dto.ShopExecution;
import com.hlt.entity.Shop;
import com.hlt.enums.ShopStateEnum;
import com.hlt.exceptions.ShopOperationException;
import com.hlt.service.ShopService;
import com.hlt.util.ImageUtil;
import com.hlt.util.PageCalculator;
import com.hlt.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    //获取店铺列表
    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);       //将页数pageIndex转化为行数rowIndex
        List<Shop> shopList = shopDao.queryShopList(shopCondition,rowIndex,pageSize);
        int count = shopDao.queryShopCount(shopCondition);  //获得店铺总数
        ShopExecution se = new ShopExecution();
        if (shopList != null){
            se.setShopList(shopList);
            se.setCount(count);
        }else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    //通过Id获取店铺信息
    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    //更新店铺信息
    @Override
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {
        if (shop == null||shop.getShopId() == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else {
            try {
            //1.判断是否更新图片
            if (thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())){    //如果传入新的图片信息
                Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                if(tempShop.getShopImg() != null){  //如果存在原有图片路径则删除
                    ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                }
                addShopImg(shop,thumbnail);   //更新图片
            }
            //2.更新店铺信息
            shop.setLastEditTime(new Date());
            int effectedNum = shopDao.updateShop(shop);
            if(effectedNum <= 0){
                return new ShopExecution(ShopStateEnum.INNER_ERROR);
            }else {
                shop = shopDao.queryByShopId(shop.getShopId());
                return new ShopExecution(ShopStateEnum.SUCCESS,shop);
            }}catch (Exception e){
                throw new ShopOperationException("modifyShop error:" + e.getMessage());
            }
        }
    }
    //添加店铺
    @Override
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {
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
                if(thumbnail.getImage() !=null){
                    //存储图片
                    try {
                        addShopImg(shop, thumbnail);
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

    private void addShopImg(Shop shop, ImageHolder thumbnail) {
        //获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(thumbnail,dest);
        shop.setShopImg(shopImgAddr);
    }

}