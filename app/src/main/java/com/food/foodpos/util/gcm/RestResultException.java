package com.food.foodpos.util.gcm;

import com.food.foodpos.util.RSCDMSG;

/**
 * Created by Andy on 2015/6/15.
 */
public class RestResultException extends RuntimeException {
    private String code = "";
    private String message = "";


    public String getCode() {
        return code;
    }

    public RestResultException(RSCDMSG code, String message) {
        this.code = code.getCode();
        this.message = code.getMessage() + ":";

    }

    public RestResultException(RSCDMSG code) {
        this(code, "");
    }

    public RestResultException(Throwable e) {
        this.code = RSCDMSG.E001.getCode();
        this.message = e.getMessage();
    }



}
