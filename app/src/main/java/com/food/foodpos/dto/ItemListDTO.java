package com.food.foodpos.dto;

import com.food.db.domainType.Meal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 15/6/23.
 */
public class ItemListDTO {

    private JsonBillMeals nowSelectBill = null;

    private Meal nowMeal = null;



    private List<JsonBillMeals> unAddList = new ArrayList<>();
    private List<JsonBillMeals> addList = new ArrayList<>();


    public List<JsonBillMeals> getUnAddList() {
        return unAddList;
    }

    public void setUnAddList(List<JsonBillMeals> unAddList) {
        this.unAddList = unAddList;
    }

    public List<JsonBillMeals> getAddList() {
        return addList;
    }

    public void setAddList(List<JsonBillMeals> addList) {
        this.addList = addList;
    }


    public JsonBillMeals getNowSelectBill() {
        return nowSelectBill;
    }

    public void setNowSelectBill(JsonBillMeals nowSelectBill) {
        this.nowSelectBill = nowSelectBill;
    }

    public Meal getNowMeal() {
        return nowMeal;
    }

    public void setNowMeal(Meal nowMeal) {
        this.nowMeal = nowMeal;
    }
}
