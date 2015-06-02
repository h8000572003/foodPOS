package com.food.foodpos.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.food.foodpos.domain.Meal;
import com.food.foodpos.domain.State;

/**
 * Created by 1109001 on 2015/6/2.
 */
public class StateDAOImpl extends AbstractDAO<State> {

    public StateDAOImpl(Context context, SQLiteDatabase db) {
        super(context,db);
    }

    @Override
    protected String[] getAllColumns() {
        return new String[]{"id", "food_id", "serial", "name", "extractDollar"};
    }

    @Override
    protected String getTableName() {
        return "State";
    }
}
