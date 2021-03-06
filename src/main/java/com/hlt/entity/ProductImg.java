package com.hlt.entity;
import java.util.Date;

//商品详情图片实体类
public class ProductImg {
    private long productImgId;
    private String imgAddr;
    private String imgDesc;
    //优先级
    private Integer priority;
    private Date createTime;
    private Long productId;

    public long getProductImgId() { return productImgId; }

    public void setProductImgId(long productImgId) { this.productImgId = productImgId; }

    public String getImgAddr() { return imgAddr; }

    public void setImgAddr(String imgAddr) { this.imgAddr = imgAddr; }

    public String getImgDesc() { return imgDesc; }

    public void setImgDesc(String imgDesc) { this.imgDesc = imgDesc; }

    public Integer getPriority() { return priority; }

    public void setPriority(Integer priority) { this.priority = priority; }

    public Date getCreateTime() { return createTime; }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public Long getProductId() { return productId; }

    public void setProductId(Long productId) { this.productId = productId; }
}
