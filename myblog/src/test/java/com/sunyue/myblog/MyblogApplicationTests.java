package com.sunyue.myblog;

import com.sunyue.myblog.commons.PageUtil;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.junit.Test;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Configuration
@SpringBootTest
public class MyblogApplicationTests {

    @Test
    public void zhengze() {
        String a = "<p><img src=\"/images/f627a915-3c71-4470-a761-we.jpg\" alt=\"\"></p>\n<p><img src=\"../images/f627a915-3c71-4470-a761-a5e60aa6e0ce.jpg\" alt=\"\"></p>\n";
        String b = "^/.*g";
        String c = "^images/(\\\\d+\\.?\\\\d*)jpg$";
        //Matcher matcher = Pattern.compile(c).matcher(a);

        Matcher m = Pattern.compile("src=\"/images/+.*?.jpg").matcher(a);
      /*  System.out.println(m.find());
        String match1=m.group();
        System.out.println(match1);*/
        while(m.find()){
            String match=m.group();
            System.out.println("match"+match);
            //Pattern.CASE_INSENSITIVE忽略'jpg'的大小写
            Matcher k=Pattern.compile("src=\"/images/+.*?.jpg",Pattern.CASE_INSENSITIVE).matcher(match);

            if(k.find()){
                //截取match子串,从下标为13(包括13)到字符串结束
                String substring = match.substring(13);
                System.out.println(substring);
            }
        }

    }

    @Test
    public void contextLoads() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
 /*       int[] b = new int[10];
        for (int i : b) {
            System.out.println("i="+i);
        }*/
        List<Integer> list1 = Arrays.stream(a).boxed().collect(Collectors.toList());
        List<List<Integer>> splitList = PageUtil.getSplitList(2, list1);
        List<Integer> integers1 = splitList.get(1);
        System.out.println(integers1);
        for (List<Integer> integers : splitList) {
            System.out.println(integers);
            for (Integer integer : integers) {
                System.out.println(integer);
            }
        }
    }

    @Test
    public void file() {
        File file = new File("F:\\myblog\\src\\main\\resources\\static\\upload\\upload\\156c6482-3459-4eda-916a-6fc1c216bbcb.jpg");
        file.delete();
    }

    @Value("${cbs.imagesPath}")
    private String mImagesPath;

    @Test
    public void indeof() {
        System.out.println(mImagesPath);
        String mImagesPath = "file:F:/myblog/src/main/resources/static/upload/";
        System.out.println(mImagesPath);
        /*if (mImagesPath.equals("") || mImagesPath.equals("${cbs.imagesPath}")) {*/
        String imagesPath = MyWebAppConfigurer.class.getClassLoader().getResource("").getPath();
        if (imagesPath.indexOf(".jar") > 0) {
            imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
        } else if (imagesPath.indexOf("classes") > 0) {
            imagesPath = "file:" + imagesPath.substring(0, imagesPath.indexOf("classes"));
        }
        imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/")) + "/images/";
        System.out.println(imagesPath);
        mImagesPath = imagesPath;
        LoggerFactory.getLogger(MyWebAppConfigurer.class).info("imagesPath=" + mImagesPath);
        System.out.println(mImagesPath);
        /* }*/
    }

    @Test
    public void subString() {
        String str = "../images/564e9942-65c9-4f05-89c8-94ac241e1ecb.jpg";

        // 第二种
        System.out.println(str.split("/")[str.split("/").length - 1]);

        // 第三种
        System.out.println(str.substring(str.lastIndexOf("/") + 1));

        // 截取最后一个“/”前面的内容
        System.out.println(str.substring(0, str.lastIndexOf("/")));
    }
}
