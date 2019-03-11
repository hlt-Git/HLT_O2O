package com.hlt.entity;
import java.util.Date;

//商品类别实体类
public class ProductCategory {
    private Long productCategoryId;
    //店铺ID
    private Long shopId;
    private String productCategoryName;
    private Integer priorityl;
    private Date createTime;

    public Long getProductCategoryId() { return productCategoryId; }

    public void setProductCategoryId(Long productCategoryId) { this.productCategoryId = productCategoryId; }

    public Long getShopId() { return shopId; }

    public void setShopId(Long shopId) { this.shopId = shopId; }

    public String getProductCategoryName() { return productCategoryName; }

    public void setProductCategoryName(String productCategoryName) { this.productCategoryName = productCategoryName; }

    public Integer getPriorityl() { return priorityl; }

    public void setPriorityl(Integer priorityl) { this.priorityl = priorityl; }

    public Date getCreateTime() { return createTime; }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }
}