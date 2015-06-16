package com.food.foodpos.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 2015/6/16.
 */
public class JsonBill extends RestObj {

    private List<BillSon> content = new ArrayList<>();

    public List<BillSon> getContent() {
        return content;
    }




}
