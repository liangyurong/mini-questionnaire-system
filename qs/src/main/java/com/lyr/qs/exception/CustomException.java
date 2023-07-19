package com.lyr.qs.exception;

import lombok.Data;

@Data
public class CustomException extends Exception {

    private int code;
    private String msg;

    public CustomException(){

    }

    public CustomException(int code){
        this.code=code;
    }

    public CustomException(String msg) {
        this.msg = msg;
    }

    public CustomException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
