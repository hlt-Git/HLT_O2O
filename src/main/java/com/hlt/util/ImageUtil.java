package com.hlt.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {

    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();      //获得资源路径
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();

    public static String generateThumbnail(InputStream thumbnailInputStream,String fileName,String targetAddr){

        String realFileName = getRandomFileName();          //随机生成不重名的文件
        String extension = getFileExtension(fileName);     //获得输入流图片的扩展名，jpg、png等
        makeDirPath(targetAddr);                            //创建目标目录
        String relativeAddr = targetAddr + realFileName + extension;    //获取相对路径
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);     //组成新生成文件的路径

        try {
            Thumbnails.of(thumbnailInputStream).size(200,200).watermark(Positions.BOTTOM_RIGHT,
                    ImageIO.read(new File(basePath + "shuiyin3.jpg")),0.25f).outputQuality(0.8f).toFile(dest);
        }catch (IOException e){
            e.printStackTrace();
        }
        return relativeAddr;
    }

    //创建目标路径所涉及的路径
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if(!dirPath.exists()){              //如果目标路径不存在，递归创建所有的目录
            dirPath.mkdirs();
        }
    }

    //获得传入图片的扩展名，jpg、png等
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }


    //生成随机文件名，当前年月日小时分钟秒钟+五位随机数
    public static String getRandomFileName() {

        int rannum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + rannum;
    }

    public static void main(String[] args) throws Exception{
        Thumbnails.of(new File("C:/Users/胡栗涛/Desktop/resource/image/xiaohuangren1.jpg"))
                .size(200,200).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "shuiyin3.jpg")),0.25f)
                .outputQuality(0.8f).toFile("C:/Users/胡栗涛/Desktop/resource/UtilImage/newxiaohuangren1.jpg");

    }

    /*
        如果storePath是文件路径则删除该文件
        如果storePath是目录路径则删除该目录下的所有文件
    */
    public static void deleteFileOrPath(String storePath){
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()){           //判断文件是否存在
            if (fileOrPath.isDirectory()){  //检查一个对象是否是文件夹
                File files[] = fileOrPath.listFiles();
                for (int i = 0;i < files.length;i++){
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }

}