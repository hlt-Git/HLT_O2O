package com.hlt.service;

import com.hlt.dto.ImageHolder;
import com.hlt.dto.ProductExecution;
import com.hlt.entity.Product;
import com.hlt.exceptions.ProductOperationException;

import java.io.InputStream;
import java.util.List;
import java.util.PriorityQueue;

public interface ProductService {

    //添加商品信息以及图片处理          重构后：商品、图片流对象、详情图片流集合对象
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException;
    //通过id查询
    Product getProductById(long productId);
    //修改商品信息以及图片处理
    ProductExecution modifyProduct(Product product,ImageHolder thumbnail,List<ImageHolder> productImgHolderList) throws ProductOperationException;
}