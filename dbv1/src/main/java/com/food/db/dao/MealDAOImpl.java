package com.food.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.food.db.domainType.Meal;


/**
 * Created by 1109001 on 2015/6/2.
 */
public class MealDAOImpl extends AbstractDAO<Meal> {

    public static AbstractDAO.DomainConverter<Meal> CONVERTER = new AbstractDAO.DomainConverter<Meal>() {

        public static final String TAG = "AbstractDAO.DomainConvertzr";

        @Override
        public Meal converter(Cursor cursor) {
            try {
                final Meal meal = new Meal();
                meal.setId(cursor.getLong(0));
                meal.setTxId(cursor.getString(1));
                meal.setName(cursor.getString(2));
                meal.setSpcialize(cursor.getString(3));
                meal.setDolloar(cursor.getString(4));
                meal.setNumber(cursor.getString(5));
                meal.setUseNumber(cursor.getString(6));
                return meal;
            } catch (Exception e) {
                Log.e(TAG, "Fail to create DomainType", e);
                throw new RuntimeException("create fail..", e);
            }
        }

        @Override
        public ContentValues converter(Meal domainType) {
            final ContentValues values = new ContentValues();
            values.put("id", domainType.getId());
            values.put("txId", domainType.getTxId());
            values.put("name", domainType.getName());
            values.put("spcialize", domainType.getSpcialize());
            values.put("dolloar", domainType.getDolloar());
            values.put("number", domainType.getNumber());
            values.put("useNumber", domainType.getUseNumber());
            return values;
        }

    };

    public MealDAOImpl(Context context, SQLiteDatabase db) {
        super(Meal.class, db);
    }

    @Override
    protected DomainConverter getDomainConvertzr() {
        return MealDAOImpl.CONVERTER;
    }

    @Override
    protected String[] getAllColumns() {
        return new String[]{"id", "txId", "name", "spcialize", "dolloar", "number", "useNumber"};
    }

    @Override
    protected String getTableName() {
        return "Meal";
    }
}
