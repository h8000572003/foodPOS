package com.food.foodpos.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.food.foodpos.domain.Bill;

/**
 * Created by 1109001 on 2015/6/2.
 */
public class BillDAOImpl extends AbstractDAO<Bill> {
    public BillDAOImpl(Context context, SQLiteDatabase db) {
        super(context, db);
    }

    @Override
    protected DomainConvertzr getDomainConvertzr() {
        return Bill.CONVERTER;
    }


    @Override
    protected String[] getAllColumns() {
        return new String[]{"id", "orderDate", "orderTime", "outOrIn"
                , "isPaid", "isMealOut", "dollar", "seat", "feature"};
    }

    @Override
    protected String getTableName() {
        return "Bill";
    }
}
