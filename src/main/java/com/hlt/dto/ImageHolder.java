package com.hlt.dto;

import java.io.InputStream;

//对所有涉及图片流及图片流名字的方法参数进行重构
public class ImageHolder {
    private String imageName;       //图片流名字
    private InputStream image;      //图片流

    public ImageHolder(String imageName, InputStream image) {
        this.imageName = imageName;
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }
}
