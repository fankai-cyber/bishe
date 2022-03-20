package com.nuedu.util;

import lombok.Getter;

@Getter
public class ResultJson {
    private Integer code;
    private String message;
    private Object obj;
    private String token;

    private ResultJson(Integer code, String message, Object obj) {
        this.code = code;
        this.message = message;
        this.obj = obj;
    }

    public ResultJson(Integer code, String message, Object obj, String token) {
        this.code = code;
        this.message = message;
        this.obj = obj;
        this.token = token;
    }

    //    public static ResultJson getResultJson(ResultCode resultCode){
//        return new ResultJson(resultCode.getCode(),resultCode.getMessage(),null);
//    }
//    public static ResultJson getResultJson(ResultCode resultCode,Object obj){
//        return new ResultJson(resultCode.getCode(),resultCode.getMessage(),obj);
//    }
//    public static ResultJson getResultJson(ResultCode resultCode,Object obj,String message){
//        return new ResultJson(resultCode.getCode(),message,obj);
//    }
//   public static ResultJson success(Object obj){
//        return getResultJson(ResultCode.SUCCESS,obj);
//   }
//    public static ResultJson success(Object obj,String message){
//        return getResultJson(ResultCode.SUCCESS,obj,message);
//    }
//    public static ResultJson error(){
//        return getResultJson(ResultCode.ERROR);
//    }
//    public static ResultJson error(String message){
//        return getResultJson(ResultCode.ERROR,message);
//    }
    public static ResultJson getResultJson(ResultCode resultCode,Object obj){
        return new ResultJson(resultCode.getCode(),resultCode.getMessage(),obj);

    }
    public static ResultJson getResultJson(ResultCode resultCode,Object obj,String message){
        return new ResultJson(resultCode.getCode(),message,obj);

    }
    public static ResultJson getResultJson(ResultCode resultCode,Object obj,String message,String token){
        return new ResultJson(resultCode.getCode(),message,obj,token);

    }
    public static ResultJson success(Object obj){
        return getResultJson(ResultCode.SUCCESS,obj);
    }
    public static ResultJson success(Object obj,String message){
        return getResultJson(ResultCode.SUCCESS,obj,message);
    }
    public static ResultJson success(Object obj,String message,String token){
        return getResultJson(ResultCode.SUCCESS,obj,message,token);
    }
    public static ResultJson error(){
        return getResultJson(ResultCode.ERROR,null);
    }
    public static ResultJson error(String message){
        return getResultJson(ResultCode.ERROR,null,message);
    }
    public static ResultJson auth(){
        return getResultJson(ResultCode.AUTH,null);
    }
    public static ResultJson permission(){
        return getResultJson(ResultCode.PERMISSION,null);
    }


    @Override
    public String toString() {
        return "ResultJson{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", obj=" + obj +
                ", token='" + token + '\'' +
                '}';
    }

    public void setToken(String token) {
        this.token = token;
    }
}
