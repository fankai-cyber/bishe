package com.nuedu.util;

import lombok.Getter;

@Getter
public enum  ResultCode {
    SUCCESS(200,"请求成功"),
    ERROR(500,"请求失败"),
    AUTH(401,"用户未登录或登陆超时"),
    PERMISSION(403,"缺少登录权限");
    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
