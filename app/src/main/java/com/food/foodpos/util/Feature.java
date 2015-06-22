package com.food.foodpos.util;

/**
 * Created by Andy on 2015/6/22.
 */
public enum Feature {


    NoSauce("不要醬油"),//
    NoParsley("不要香菜"),//
    UseBox("使用盒裝"),//
    TakeOut("外帶"),//
    TakeOff("拆"),
    NoAddAny("清燙"),
    MoreCook("煮軟"),
    AddMarinade("加滷汁");

    //
    ;

    Feature(String name) {
        this.name = name;
    }

    String name = "";

    public String getName() {
        return name;
    }
}
