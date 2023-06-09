package com.mychatgpt.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;

@Component
@Slf4j
public class MyFileUtil {
    public static final String resourcePath="E:/JavaProjectSpace/smile-live-resource/";
    public static final String AVATAR_PATH="data/avatar/";
    public static final String COVER_PATH="data/cover/";
    public static final String DEFAULT_IMAGE_PATH=resourcePath+"data/default/smile-world.jpg";
    public String saveImage(MultipartFile file,String beginPath){
        try{
            //生成随机文件名
            String filename=IdUtil.fastSimpleUUID ()+"."+FileNameUtil.getSuffix (file.getOriginalFilename ());
            //生成相对路径
            String path=beginPath+filename;
            //生成绝对路径
            String fullPath=resourcePath+path;
            log.info("fullpath-->{}",fullPath);
            //创建头像文件
            File imageFile = FileUtil.touch (fullPath);
            log.info ("avatarFile-->{}",imageFile.getPath ());
            //写入头像文件
            BufferedOutputStream out = FileUtil.getOutputStream (imageFile);
            out.write (file.getBytes ());
            out.flush ();
            out.close ();
            //返回相对路径
            return path;
        }catch (Exception e){
            e.printStackTrace ();
            return null;
        }
    }
    public byte[] getImage(String filepath){
        try{
            String path=resourcePath+filepath;
            File file = FileUtil.file (path);
            if(file==null){
                throw new Exception ();
            }
            BufferedInputStream input = FileUtil.getInputStream (file);
            byte[] b=new byte[1024*1024*2];
            input.read (b);
            input.close ();
            return b;
        }catch (Exception e){
            try{
                BufferedInputStream input = FileUtil.getInputStream (MyFileUtil.DEFAULT_IMAGE_PATH);
                byte[] b=new byte[1024*1024*2];
                input.read (b);
                input.close ();
                return b;
            }catch (Exception e2){
               return null;
            }
        }
    }
    public void delImage(String filepath){
        try{
            String path=resourcePath+filepath;
            if(FileUtil.exist (path)&&FileUtil.isFile (path)) {
                FileUtil.del (path);
            }
        }catch (Exception e){
            e.printStackTrace ();
        }
    }
}
