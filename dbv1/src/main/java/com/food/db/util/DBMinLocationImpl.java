package com.food.db.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.food.db.domainType.DomainType;
import com.food.db.domainType.Table;

import java.util.List;

/**
 * Created by 1109001 on 2015/6/4.
 */
public class DBMinLocationImpl implements DBMain {

    private static final String TAG = "DBMinLocationImpl";
    private SQLiteDatabase db;

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public void insert(DomainType type) {
        final Table table = type.getClass().getAnnotation(Table.class);
        final ContentValues values = CommonUtil.getValueByDomain(type);
        this.db.insert(table.table(), null, values);

    }

    @Override
    public void insert(List<DomainType> types) {
        for (DomainType type : types) {
            this.insert(type);
        }
    }


    @Override
    public void delete(DomainType type) {
        final Table table = type.getClass().getAnnotation(Table.class);

        this.db.delete(table.table(), table.keyName() + "=?", new String[]{CommonUtil.getObject(type, table.keyName()).toString()});
    }

    @Override
    public void delete(List<DomainType> types) {
        for (DomainType type : types) {
            this.delete(type);
        }
    }

    @Override
    public void modfiy(DomainType type) {
        final Table table = type.getClass().getAnnotation(Table.class);
        final ContentValues values = CommonUtil.getValueByDomain(type);
        this.db.update(table.table(), values, table.keyName() + "=?", new String[]{CommonUtil.getObject(type, table.keyName()).toString()});


    }

    @Override
    public void modfiy(List<DomainType> types) {
        for (DomainType type : types) {
            this.modfiy(type);
        }
    }

    @Override
    public <T extends DomainType> List<T> query(Class<T> pClass, String sql, String[] selectionArgs) {
        Cursor cursor = this.db.rawQuery(sql, selectionArgs);
        List result = CommonUtil.getValueByDomain(cursor, pClass);
        cursor.close();
        return result;
    }

    @Override
    public void beginTransaction() {
        this.db.beginTransaction();
    }

    @Override
    public void setTransactionSuccessful() {
        this.db.setTransactionSuccessful();
    }

    @Override
    public void endTransaction() {
        this.db.endTransaction();
    }

    @Override
    public void close() {
        if (this.db != null) {
            this.db.close();
        }

    }

}
