package com.hlt.entity;
import java.util.Date;

//区域实体类
public class Area {
    private Integer areaId;
    private String areaName;
    //权重
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;

    public Area() { super(); }

    public Area(Integer areald, String areaName, Integer priority, Date createTime, Date lastEditTime) {
        this.areaId = areald;
        this.areaName = areaName;
        this.priority = priority;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
    }

    @Override
    public String toString() {
        return "Area{" +
                "areald=" + areaId +
                ", areaName='" + areaName + '\'' +
                ", priority=" + priority +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                '}';
    }

    public Integer getAreald() { return areaId; }

    public void setAreald(Integer areald) { this.areaId = areald; }

    public String getAreaName() { return areaName; }

    public void setAreaName(String areaName) { this.areaName = areaName; }

    public Integer getPriority() { return priority; }

    public void setPriority(Integer priority) { this.priority = priority; }

    public Date getCreateTime() { return createTime; }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public Date getLastEditTime() { return lastEditTime; }

    public void setLastEditTime(Date lastEditTime) { this.lastEditTime = lastEditTime; }
}
