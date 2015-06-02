package com.food.foodpos.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.food.foodpos.domain.DomainType;
import com.food.foodpos.util.DBHelp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by 1109001 on 2015/6/2.
 */
public abstract class AbstractDAO<T extends DomainType> {

    private SQLiteDatabase db;

    public AbstractDAO(Context context, SQLiteDatabase db) {

        this.db = db;
    }

    protected abstract String[] getAllColumns();

    protected abstract String getTableName();

    public T getDataById(Long id) {
        List result = new ArrayList();

        // Name of the columns we want to select
        String[] tableColumns = this.getAllColumns();

        // Query the database
        Cursor cursor = db.query(this.getTableName(), tableColumns, "id=?", new String[]{id.toString()}, null, null, "id desc");
        cursor.moveToFirst();

        // Iterate the results

        if (result.isEmpty()) {
            return null;
        }

        return (T) result.get(0);

    }

    public void delete(T src) {
        db.delete(this.getTableName(), "id=?", new String[]{src.getId().toString()});
    }


}
