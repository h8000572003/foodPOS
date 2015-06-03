package com.food.foodpos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.food.foodpos.domain.DomainType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1109001 on 2015/6/2.
 */
public abstract class AbstractDAO<T extends DomainType> {
    private SQLiteDatabase db;

    public AbstractDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public interface DomainConverter<T extends DomainType> {
        /**
         * 轉換成domain
         *
         * @param cursor
         * @return
         */
        T converter(Cursor cursor);

        /**
         * 主換成map
         *
         * @param domainType
         * @return
         */
        ContentValues converter(T domainType);
    }


    protected abstract DomainConverter getDomainConvertzr();

    protected abstract String[] getAllColumns();

    protected abstract String getTableName();


    public final T getDataById(Long id) {
        List result = new ArrayList();

        // Name of the columns we want to select
        String[] tableColumns = this.getAllColumns();

        // Query the database
        Cursor cursor = db.query(this.getTableName(), tableColumns, "id=?", new String[]{id.toString()}, null, null, "id desc");
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            result.add(this.getDomainConvertzr().converter(cursor));
            cursor.moveToNext();        //將指標移至下一筆資料
        }

        if (result.isEmpty()) {
            return null;
        }

        return (T) result.get(0);

    }

    public final void delete(T src) {

        db.delete(this.getTableName(), "id=?", new String[]{src.getId().toString()});
    }

    public final void insert(T src) {

        if (db.insert(this.getTableName(), null, this.getDomainConvertzr().converter(src)) == -1) {
            throw new RuntimeException("insert error..");
        }

    }

    public final void modfiyByColumn(List<String> columns, List<String> values, Long id) {
        ContentValues value = new ContentValues();
        for (int i = 0; i < columns.size(); i++) {

            value.put(columns.get(i), values.get(i));
        }
        db.update(this.getTableName(), value, "id=?", new String[]{id.toString()});
    }

    public final void modfiy(DomainType domainType) {

    }

}
