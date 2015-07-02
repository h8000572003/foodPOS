package com.food.foodpos.util;

/**
 * Created by 1109001 on 2015/7/2.
 */
public enum RSCDMSG {

    U001("U001", "未知錯誤"),//
    E001("E001", "錯誤"),//

    ;

    RSCDMSG(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    private String code = "";
    private  String message = "";

    public  static RSCDMSG lookUp(String code) {


        for (RSCDMSG mMsg : RSCDMSG.values()) {
            if (mMsg.code.equals(code)) {
                return mMsg;
            }
        }
        return RSCDMSG.U001;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}