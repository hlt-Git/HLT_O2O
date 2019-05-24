package com.hlt.dao;

import com.hlt.entity.ProductImg;
import java.util.List;

public interface ProductImgDao {
    //查询详情图片列表
     List<ProductImg> queryProductImgList(long productId);
    //批量添加商品详情图片
    int batchInsertProductImg(List<ProductImg> productImgList);
    //删除指定商品下的详情图
    int deleteProductImgByProductId(long productId);
}