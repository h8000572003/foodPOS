package com.food.foodpos.dto;

import com.food.db.domainType.Meal;

/**
 * Created by 1109001 on 2015/6/8.
 */
public class MealDTO {
    private Meal meal;
    private boolean isOut;

    public MealDTO(Meal meal) {
        this.meal = meal;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public boolean isOut() {
        return isOut;
    }

    public void setIsOut(boolean isOut) {
        this.isOut = isOut;
    }
}
