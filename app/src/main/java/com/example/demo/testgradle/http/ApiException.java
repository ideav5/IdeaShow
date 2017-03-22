package com.example.demo.testgradle.http;

/**
 * Created by codeest on 2016/8/4.
 */
public class ApiException extends Exception {
    public ApiException(String errCode, String msg) {
        super(msg);
    }
}
