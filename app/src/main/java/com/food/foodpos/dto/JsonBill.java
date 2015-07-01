package com.food.foodpos.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 2015/6/16.
 */
public class JsonBill extends RestObj {

    private List<JsonBillMeals> content = new ArrayList<>();

    public List<JsonBillMeals> getContent() {
        return content;
    }




}
