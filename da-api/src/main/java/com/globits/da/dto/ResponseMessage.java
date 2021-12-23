package com.globits.da.dto;

public class ResponseMessage {
    private String msg;

    public ResponseMessage() {
    }

    public ResponseMessage(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
