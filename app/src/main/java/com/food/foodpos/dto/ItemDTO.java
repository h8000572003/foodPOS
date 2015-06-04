package com.food.foodpos.dto;

import com.food.db.domainType.Bill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1109001 on 2015/6/4.
 */
public class ItemDTO {
    private List<Bill> bill = new ArrayList<Bill>();

    public List<Bill> getBill() {
        return bill;
    }

    public void setBill(List<Bill> bill) {
        this.bill = bill;
    }
}
