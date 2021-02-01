package com.sunyue.myblog.commons;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpUtils {
    //这个地方和FileController一致
    private static final String URL = "src=\"/images/.*?.jpg";
    /*
     * 图片*/
    private static final String NUM = "^-?\\d+(\\.\\d+)?$";
    /*
    * 密码*/
    private   static final String PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
    /**
     * 验证手机号
     */
    private static final String PHONE = "^1[34578]\\d{9}$";

    /**
     * 验证邮箱地址
     */
    private static final String EMAIL = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}";

    /**
     * 验证手机号
     *
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {

        return !(phone.matches(PHONE));
    }
    public static boolean checkNUM(String num) {

        return !(num.matches(NUM));
    }
    /*
    * 验证密码*/
    public static boolean checkPassword(String password){
        return !(password.matches(PASSWORD));
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        return !(email.matches(EMAIL));
    }

    /*
    * 验证是否为空*/
    public static boolean checkNull(String s){
        if(s == null || s.length() <= 0 || s.equals("") || s == ""){
            return true;
        }
        return false;
    }
    /*验证图片地址*/
    public static List<String> checkImgUrl(String imgUrl){
        List<String> urlList = new ArrayList<>();
        Matcher m = Pattern.compile(URL).matcher(imgUrl);
        while(m.find()){
            String match=m.group();
            //Pattern.CASE_INSENSITIVE忽略'jpg'的大小写
            Matcher k=Pattern.compile(URL,Pattern.CASE_INSENSITIVE).matcher(match);

            if(k.find()){
                //截取match子串,从下标为13(包括13)到字符串结束
                urlList.add(match.substring(13));
            }
        }
        return urlList;
    }
}
