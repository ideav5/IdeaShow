package com.example.demo.testgradle.http.base.net;

/**
 * Created by idea on 2017/3/23.
 */

public class RabHttpResponse<T> {
    /**
     * result : true
     * code : 0
     * message : 操作成功
     * data : null
     */

    private boolean result;
    private int code;
    private String message;
    private T data;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
