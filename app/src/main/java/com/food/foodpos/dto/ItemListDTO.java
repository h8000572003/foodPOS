package com.food.foodpos.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 15/6/23.
 */
public class ItemListDTO {
    private List<BillSon> unAddList = new ArrayList<>();
    private List<BillSon> addList = new ArrayList<>();


    public List<BillSon> getUnAddList() {
        return unAddList;
    }

    public void setUnAddList(List<BillSon> unAddList) {
        this.unAddList = unAddList;
    }

    public List<BillSon> getAddList() {
        return addList;
    }

    public void setAddList(List<BillSon> addList) {
        this.addList = addList;
    }
}
