package com.food.foodpos.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.food.foodpos.domain.Bill;
import com.food.foodpos.domain.Meal;

/**
 * Created by 1109001 on 2015/6/2.
 */
public class MealDAOImpl extends AbstractDAO<Meal> {

    public static AbstractDAO.DomainConvertzr<Meal> CONVERTER = new AbstractDAO.DomainConvertzr<Meal>() {

        public static final String TAG = "AbstractDAO.DomainConvertzr";

        @Override
        public Meal converter(Cursor cursor) {
            try {
                final Meal meal = new Meal();
                meal.setId(cursor.getLong(0));
                meal.setBillId(cursor.getLong(1));
                meal.setName(cursor.getString(2));
                meal.setSpcialize(cursor.getString(3));
                meal.setDolloar(cursor.getString(4));
                meal.setNumber(cursor.getString(5));
                return meal;
            } catch (Exception e) {
                Log.e(TAG, "Fail to create DomainType");
                throw new RuntimeException("create fail..", e);
            }
        }

    };

    public MealDAOImpl(Context context, SQLiteDatabase db) {
        super( db);
    }

    @Override
    protected DomainConvertzr getDomainConvertzr() {
        return MealDAOImpl.CONVERTER;
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
