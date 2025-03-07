package com.example.common.handler;

import com.example.common.exception.BaseException;
import com.example.common.vo.ResultDataVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义异常处理器
 */
@RestControllerAdvice
public class RestExceptionHandler {
    /**
     * 捕获自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultDataVO exception(BaseException e){
        return ResultDataVO.fail(e.getCode(),e.getMessage());
    }

    /**
     * 捕获参数异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ResultDataVO handleIllegalArgumentException(MethodArgumentNotValidException e) {
        return ResultDataVO.fail(103,e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

}
