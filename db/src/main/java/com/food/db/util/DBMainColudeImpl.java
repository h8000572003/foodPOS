package com.food.db.util;

import android.text.TextUtils;
import android.util.Log;

import com.food.db.domainType.Column;
import com.food.db.domainType.DomainType;
import com.food.db.domainType.Table;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.codec.binary.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1109001 on 2015/6/4.
 */
public class DBMainColudeImpl implements DBMain {
    private static final String TAG = "DBMainColudeImpl";

    @Override
    public void insert(DomainType type) {


    }

    @Override
    public void insert(List<DomainType> types) {

    }

    @Override
    public void delete(DomainType type) {

    }

    @Override
    public void delete(List<DomainType> types) {

    }

    @Override
    public void modfiy(DomainType type) {

    }

    @Override
    public void modfiy(List<DomainType> types) {

    }

    @Override
    public <T extends DomainType> List<T> query(Class<T> pClass, String where, String[] selectionArgs) {
        final Table table = pClass.getAnnotation(Table.class);

        final ParseQuery<ParseObject> query = ParseQuery.getQuery(table.table());


        final String[] keys = TextUtils.split(where, "and");

        if (keys.length > 0) {
            for (int index = 0; index < keys.length; index++) {
                query.whereEqualTo(keys[index], selectionArgs[index]);
            }
        }


        final List<ParseObject> parseObjects;


        final List<T> results = new ArrayList<>();
        try {
            parseObjects = query.find();
            final Field[] fs = pClass.getDeclaredFields();
            for (ParseObject obj : parseObjects) {
                final T newObject = pClass.newInstance();
                for (Field f : fs) {
                    final Column column = f.getAnnotation(Column.class);
                    Object value = null;
                    if (column.isNum()) {
                        value = obj.getLong(column.column());
                    } else {
                        value = obj.getString(column.column());
                    }
                    setObject(newObject, column.column(), value);

                }
                results.add(newObject);
            }

        } catch (Exception e) {
            Log.e(TAG, "e:" + e.getMessage(), e);
            throw new RuntimeException("e:" + e.getMessage());
        } finally {

        }


        return results;
    }

    private static void setObject(Object object, String name, Object value) {
        Field f = null;

        try {
            f = object.getClass().getDeclaredField(name);
            f.setAccessible(true);
            f.set(object, value);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            f.setAccessible(false);
        }


    }

    @Override
    public void beginTransaction() {

    }

    @Override
    public void setTransactionSuccessful() {

    }

    @Override
    public void endTransaction() {

    }

    @Override
    public void close() {

    }
}
