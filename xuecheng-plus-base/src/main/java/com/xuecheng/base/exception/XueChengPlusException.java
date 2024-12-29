package com.xuecheng.base.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XueChengPlusException extends RuntimeException{

    private static final Logger log = LoggerFactory.getLogger(XueChengPlusException.class);

    public XueChengPlusException(String msg){
        super(msg);
    }

    public static void cast(String msg){
        throw new XueChengPlusException(msg);
    }

    public static void cast(CommonError commonError){
        throw new XueChengPlusException(commonError.getErrMessage());
    }

}
