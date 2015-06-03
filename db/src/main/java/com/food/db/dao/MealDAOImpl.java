package com.food.db.dao;

import android.content.ContentValues;
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

    public static AbstractDAO.DomainConverter<Meal> CONVERTER = new AbstractDAO.DomainConverter<Meal>() {

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
                Log.e(TAG, "Fail to create DomainType",e);
                throw new RuntimeException("create fail..", e);
            }
        }

        @Override
        public ContentValues converter(Meal domainType) {
            final ContentValues values = new ContentValues();
            values.put("id", domainType.getId());
            values.put("billId", domainType.getBillId());
            values.put("name", domainType.getName());
            values.put("spcialize", domainType.getSpcialize());
            values.put("dolloar", domainType.getDolloar());
            values.put("number", domainType.getNumber());
            return values;
        }

    };

    public MealDAOImpl(Context context, SQLiteDatabase db) {
        super(db);
    }

    @Override
    protected DomainConverter getDomainConvertzr() {
        return MealDAOImpl.CONVERTER;
    }

    @Override
    protected String[] getAllColumns() {
        return new String[]{"id", "billId", "name", "spcialize", "dolloar", "number"};
    }

    @Override
    protected String getTableName() {
        return "Meal";
    }
}
