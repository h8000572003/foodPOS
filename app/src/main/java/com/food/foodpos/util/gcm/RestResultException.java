package com.food.foodpos.util.gcm;

/**
 * Created by Andy on 2015/6/15.
 */
public class RestResultException extends RuntimeException {
    private String code = "";

    private String message = "";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
