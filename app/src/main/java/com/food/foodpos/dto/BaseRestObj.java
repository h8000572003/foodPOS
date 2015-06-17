package com.food.foodpos.dto;

/**
 * Created by Andy on 2015/6/17.
 */
public class BaseRestObj extends RestObj {

    private String content = "";

    @Override
    protected Object getContent() {
        return content;
    }
}
