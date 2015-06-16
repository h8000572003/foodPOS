package com.food.foodpos.dto;

/**
 * Created by Andy on 2015/6/16.
 */
public abstract class RestObj {

    private String code = "";
    private String message = "";

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

    protected abstract Object getContent();

    @Override
    public String toString() {
        return "RestObj{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
