package com.android.smsutil.bean;

import java.io.Serializable;

public class Result<T> implements Serializable {
    String code = null;
    String message = "请求失败";
    T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
