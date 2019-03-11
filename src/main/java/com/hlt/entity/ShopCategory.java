package com.hlt.entity;
import java.util.Date;

//店铺类别实体类
public class ShopCategory {
    private Long shopCategoryId;
    private String shopCategoryName;
    //店铺实体类的描述
    private String shopCategoryDesc;
    //图片的链接地址
    private String shopCategoryImg;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    //上级ID，因为上级ID也是店铺类别，所以是对象类型
    private ShopCategory parent;

    public Long getShopCategoryId() { return shopCategoryId; }

    public void setShopCategoryId(Long shopCategoryId) { this.shopCategoryId = shopCategoryId; }

    public String getShopCategoryName() { return shopCategoryName; }

    public void setShopCategoryName(String shopCategoryName) { this.shopCategoryName = shopCategoryName; }

    public String getShopCategoryDesc() { return shopCategoryDesc; }

    public void setShopCategoryDesc(String shopCategoryDesc) { this.shopCategoryDesc = shopCategoryDesc; }

    public String getShopCategoryImg() { return shopCategoryImg; }

    public void setShopCategoryImg(String shopCategoryImg) { this.shopCategoryImg = shopCategoryImg; }

    public Integer getPriority() { return priority; }

    public void setPriority(Integer priority) { this.priority = priority; }

    public Date getCreateTime() { return createTime; }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public Date getLastEditTime() { return lastEditTime; }

    public void setLastEditTime(Date lastEditTime) { this.lastEditTime = lastEditTime; }

    public ShopCategory getParent() { return parent; }

    public void setParent(ShopCategory parentId) { this.parent = parentId; }
}
