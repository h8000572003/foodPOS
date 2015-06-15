package com.food.foodpos.util.gcm;

/**
 * Created by Andy on 2015/6/15.
 */
public class RestObject {
    private String code = "";
    private String mesaage = "";
    private String content = "";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMesaage() {
        return mesaage;
    }

    public void setMesaage(String mesaage) {
        this.mesaage = mesaage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "RestObject{" +
                "code='" + code + '\'' +
                ", mesaage='" + mesaage + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
