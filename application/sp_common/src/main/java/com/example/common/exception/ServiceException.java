package com.example.common.exception;

/**
 * 服务器异常
 */
public class ServiceException extends BaseException{

    public ServiceException(String message, int code) {
        super(message, code);
    }
}
