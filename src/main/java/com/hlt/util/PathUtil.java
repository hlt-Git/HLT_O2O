package com.hlt.util;

//获取图片路径
public class PathUtil {

    private static String seperator = System.getProperty("file.separator");     //获得系统的分隔符
    public static String getImgBasePath(){      //获得项目存放图片的根路径

        String os = System.getProperty("os.name");                      //获得操作系统的名称
        String basePath = "";
        if(os.toLowerCase().startsWith("win")){
            basePath = "C:/Users/胡栗涛/Desktop/resource/UtilImage/";
        }else {
            basePath = "/home/hulitao/image/";
        }
        basePath = basePath.replace("/",seperator);
        return basePath;
    }

   public static String getShopImagePath(long shopId){      //获得店铺图片的子路径
        String imagePath = "upload/item/shop/" + shopId + "/";
        return imagePath.replace("/",seperator);
   }
}