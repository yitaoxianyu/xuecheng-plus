package com.xuecheng.base.exception;

public class ErrorResponse {

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    private String errMsg;

    public ErrorResponse(String errMsg){
        this.errMsg = errMsg;
    }


}
