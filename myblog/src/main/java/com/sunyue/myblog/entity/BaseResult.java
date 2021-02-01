package com.sunyue.myblog.entity;

import java.io.Serializable;

public class BaseResult implements Serializable {
    private static final int STATUS_200 = 200;
    private static final int FAIL_500 = 500;
    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    private static BaseResult getBaseResult(int status,String message){
        BaseResult baseResult = new BaseResult();
        baseResult.setMessage(message);
        baseResult.setStatus(status);
        return baseResult;
    }
    public static BaseResult success(){
        return getBaseResult(STATUS_200,"成功");
    }
    public static BaseResult success(String message){
        return getBaseResult(STATUS_200,message);
    }
    public static BaseResult fail(){
        return getBaseResult(FAIL_500,"失败");
    }
    public static BaseResult fail(String message){
        return getBaseResult(FAIL_500,message);
    }
    public static BaseResult fail(int status,String message){
        return getBaseResult(status,message);
    }
}
