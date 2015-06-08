package com.food.foodpos.dto;

import com.food.db.domainType.Bill;
import com.food.db.domainType.Meal;

import java.util.List;

/**
 * Created by 1109001 on 2015/6/8.
 */
public class BillAndMeals {
    private Bill bill;
    private boolean ioOrder = false;


    private List<MealDTO> mealList;

    public boolean isIoOrder() {
        return ioOrder;
    }

    public void setIoOrder(boolean ioOrder) {
        this.ioOrder = ioOrder;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public List<MealDTO> getMealList() {
        return mealList;
    }

    public void setMealList(List<MealDTO> mealList) {
        this.mealList = mealList;
    }
}
