package com.hlt.dao;

import com.hlt.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {
    //分页查询店铺，可输入的条件有：店铺名(模糊),店铺状态,店铺类别,区域Id,owner
    //shopCondition：查询的条件      rowIndex：从第几行开始取数据        pageSize：返回的条数
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);

    int queryShopCount(@Param("shopCondition") Shop shopCondition); //返回queryShopList总数

    Shop queryByShopId(long shopId);        //查询店铺

    int insertShop(Shop shop);              //增加店铺，返回1代表插入成功，返回-1代表插入失败

    int updateShop(Shop shop);              //更新店铺的信息

}