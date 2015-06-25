package com.food.foodpos.dto;

import com.food.db.domainType.Meal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 15/6/23.
 */
public class ItemListDTO {

    private BillSon nowSelectBill = null;

    private Meal nowMeal = null;



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


    public BillSon getNowSelectBill() {
        return nowSelectBill;
    }

    public void setNowSelectBill(BillSon nowSelectBill) {
        this.nowSelectBill = nowSelectBill;
    }

    public Meal getNowMeal() {
        return nowMeal;
    }

    public void setNowMeal(Meal nowMeal) {
        this.nowMeal = nowMeal;
    }
}
