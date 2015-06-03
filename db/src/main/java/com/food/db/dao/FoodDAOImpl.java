package com.food.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.food.foodpos.domain.Food;

/**
 * Created by 1109001 on 2015/6/2.
 */
public class FoodDAOImpl extends AbstractDAO<Food> {
    public FoodDAOImpl(Context context, SQLiteDatabase db) {
        super(db);
    }

    public static AbstractDAO.DomainConverter<Food> CONVERTER = new AbstractDAO.DomainConverter<Food>() {

        public static final String TAG = "AbstractDAO.DomainConvertzr<Food>";

        @Override
        public Food converter(Cursor cursor) {
            try {
                final Food food = new Food();
                food.setId(cursor.getLong(0));
                food.setName(cursor.getString(1));
                food.setDollar(cursor.getString(2));
                return food;
            } catch (Exception e) {
                Log.e(TAG, "Fail to create DomainType");
                throw new RuntimeException("create fail..", e);
            }
        }

        @Override
        public ContentValues converter(Food domainType) {
            final ContentValues values = new ContentValues();
            values.put("id", domainType.getId());
            values.put("name", domainType.getName());
            values.put("dollar", domainType.getDollar());

            return values;
        }

    };

    @Override
    protected DomainConverter getDomainConvertzr() {
        return FoodDAOImpl.CONVERTER;
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
