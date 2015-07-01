package com.food.foodpos.dto;

import com.food.db.domainType.Meal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 2015/6/16.
 */
public class JsonBillMeals {
    private com.food.db.domainType.Bill bill = new com.food.db.domainType.Bill();
    private List<Meal> meals = new ArrayList<>();

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public com.food.db.domainType.Bill getBill() {
        return bill;
    }

    public void setBill(com.food.db.domainType.Bill bill) {
        this.bill = bill;
    }

    @Override
    public String toString() {
        return "JsonBillMeals{" +
                "bill=" + bill +
                ", meals=" + meals +
                '}';
    }
}
