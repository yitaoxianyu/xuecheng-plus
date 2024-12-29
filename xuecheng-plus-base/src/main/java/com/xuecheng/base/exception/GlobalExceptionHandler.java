package com.xuecheng.base.exception;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse paramException(MethodArgumentNotValidException e){
        List<String> errorsFields = new ArrayList<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorsFields.add(fieldError.getField());
        }
        String errMsg = StringUtils.join(errorsFields, ",");
        ErrorResponse errorResponse = new ErrorResponse(errMsg);
        errorResponse.setErrMsg("以下字段存在问题" + errMsg);
        return errorResponse;
    }
}
