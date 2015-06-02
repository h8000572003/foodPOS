package com.food.foodpos.dao;

import android.content.Context;

import com.food.foodpos.domain.Food;

/**
 * Created by 1109001 on 2015/6/2.
 */
public class FoodDAOImpl extends AbstractDAO<Food> {
    public FoodDAOImpl(Context context) {
        super(context);
    }

    @Override
    protected String[] getAllColumns() {
        return new String[]{"id", "name", "dollar"};
    }

    @Override
    protected String getTableName() {
        return "Food";
    }
}
