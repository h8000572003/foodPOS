package com.food.foodpos.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.food.foodpos.domain.Bill;
import com.food.foodpos.domain.Meal;

/**
 * Created by 1109001 on 2015/6/2.
 */
public class MealDAOImpl extends AbstractDAO<Meal> {

    public MealDAOImpl(Context context, SQLiteDatabase db) {
        super(context, db);
    }

    @Override
    protected DomainConvertzr getDomainConvertzr() {
        return Meal.CONVERTER;
    }

    @Override
    protected String[] getAllColumns() {
        return new String[]{"id", "bill_id", "name", "spcialize", "dolloar", "number"};
    }

    @Override
    protected String getTableName() {
        return "Meal";
    }
}
