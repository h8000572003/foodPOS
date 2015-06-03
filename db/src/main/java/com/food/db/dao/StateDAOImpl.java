package com.food.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.food.foodpos.domain.State;

/**
 * Created by 1109001 on 2015/6/2.
 */
public class StateDAOImpl extends AbstractDAO<State> {

    public StateDAOImpl(Context context, SQLiteDatabase db) {
        super(db);
    }


    public static AbstractDAO.DomainConverter<State> CONVERTER = new AbstractDAO.DomainConverter<State>() {

        public static final String TAG = "AbstractDAO.DomainConvertzr";

        @Override
        public State converter(Cursor cursor) {
            try {
                final State meal = new State();
                meal.setId(cursor.getLong(0));
                meal.setFoodId(cursor.getLong(1));
                meal.setSerial(cursor.getLong(2));
                meal.setName(cursor.getString(3));
                meal.setExtractDollar(cursor.getString(4));
                return meal;
            } catch (Exception e) {
                Log.e(TAG, "Fail to create DomainType");
                throw new RuntimeException("create fail..", e);
            }
        }

        @Override
        public ContentValues converter(State domainType) {
            final ContentValues values = new ContentValues();
            values.put("id", domainType.getId());
            values.put("foodId", domainType.getFoodId());
            values.put("serial", domainType.getSerial());
            values.put("name", domainType.getName());

            values.put("extractDollar", domainType.getExtractDollar());

            return values;
        }

    };

    @Override
    protected DomainConverter getDomainConvertzr() {
        return StateDAOImpl.CONVERTER;
    }

    @Override
    protected String[] getAllColumns() {
        return new String[]{"id", "foodId", "serial", "name", "extractDollar"};
    }

    @Override
    protected String getTableName() {
        return "State";
    }
}
