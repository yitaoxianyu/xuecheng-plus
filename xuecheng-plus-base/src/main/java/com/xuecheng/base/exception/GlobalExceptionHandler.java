package com.xuecheng.base.exception;


import jdk.internal.org.jline.utils.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


//这个注解相当于一个controller的增强
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(XueChengPlusException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse customException(XueChengPlusException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        log.error("系统异常:{}",errorResponse.getErrMsg(),e);
        return errorResponse;
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse systemException(Exception e){
        return new ErrorResponse(CommonError.UNKOWN_ERROR.getErrMessage());
    }

}
