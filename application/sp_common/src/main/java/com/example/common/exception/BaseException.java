package com.example.common.exception;

/**
 * 服务器异常
 */
public class BaseException extends RuntimeException{
    private int code;//状态码

    public BaseException(String message, int code) {
        super(message);
        this.code = code;
    }
    public int getCode() {
        return code;
    }

}
