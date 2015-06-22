package com.food.foodpos.dto;

import java.io.Serializable;

/**
 * Created by Andy on 2015/6/22.
 */
public class OrderAddItem implements Serializable{
    private String name = "";
    private String no;
    private String feature;
    private int dollar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public int getDollar() {
        return dollar;
    }

    public void setDollar(int dollar) {
        this.dollar = dollar;
    }
}
