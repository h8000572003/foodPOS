package com.food.foodpos.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 2015/6/22.
 */
public class OrderDTO {
    private List<OrderAddItem>items=new ArrayList<>();

    public List<OrderAddItem> getItems() {
        return items;
    }

    public void setItems(List<OrderAddItem> items) {
        this.items = items;
    }
}
