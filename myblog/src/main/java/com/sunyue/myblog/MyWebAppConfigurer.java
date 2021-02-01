package com.sunyue.myblog;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 资源映射路径
 */
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
    //跨域
    /*   @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
    }*/

    /* @Override
     public void addResourceHandlers(ResourceHandlerRegistry registry) {
         registry.addResourceHandler("/upload/**").addResourceLocations("file:F:/img/");
     }*/
    // 获取配置文件中图片的路径
    @Value("${cbs.imagesPath}")
    private String mImagesPath;


    // 访问图片方法
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //埋个坑，上线时判断路径用
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
            if (mImagesPath.equals("") || mImagesPath.equals("${cbs.imagesPath}")) {
                String imagesPath = MyWebAppConfigurer.class.getClassLoader().getResource("").getPath();
                if (imagesPath.indexOf(".jar") > 0) {
                    imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
                } else if (imagesPath.indexOf("classes") > 0) {
                    imagesPath = "file:" + imagesPath.substring(0, imagesPath.indexOf("classes"));
                }
                imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/")) + "/images/";
                mImagesPath = imagesPath;
            }
            LoggerFactory.getLogger(MyWebAppConfigurer.class).info("imagesPath1=" + mImagesPath);
            //这个地方也只能是绝对路径
            registry.addResourceHandler("/images/**").addResourceLocations("file:" + mImagesPath);
            WebMvcConfigurer.super.addResourceHandlers(registry);
        } else {  //linux 和mac
            // imagesPath2=/home/sunyue/docker/upload/
            LoggerFactory.getLogger(MyWebAppConfigurer.class).info("imagesPath=" + mImagesPath);
            //registry.addResourceHandler("/images/**").addResourceLocations(mImagesPath);
            //绝对路径
            registry.addResourceHandler("/images/**").addResourceLocations("file:" + mImagesPath);
            WebMvcConfigurer.super.addResourceHandlers(registry);
        }


    }

}