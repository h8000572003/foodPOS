package com.food.db.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.food.db.domainType.Column;
import com.food.db.domainType.DomainType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1109001 on 2015/6/4.
 */
public class CommonUtil {
    private static final String TAG = "CommonUtil";


    public static ContentValues getValueByDomain(DomainType type) {
        final Field[] fs = type.getClass().getDeclaredFields();
        ContentValues values = new ContentValues();
        for (Field f : fs) {
            if (f.isAnnotationPresent(Column.class)) {
                final Column column = f.getAnnotation(Column.class);
                Object obj = getObject(type, column.column());
                if (obj instanceof String) {
                    values.put(column.column(), (String) obj);
                } else {
                    values.put(column.column(), (Long) obj);
                }
            }
        }
        return values;
    }

    public static <T extends DomainType> List<T> getValueByDomain(Cursor cursor, Class<T> pClass) {
        List<T> result = new ArrayList<T>();
        try {
            final Field[] fs = pClass.getDeclaredFields();
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                final T newObject = pClass.newInstance();
                for (int j = 0; j < fs.length; j++) {
                    if (fs[j].isAnnotationPresent(Column.class)) {
                        final Column column = fs[j].getAnnotation(Column.class);
                        Object value = null;
                        Log.d(TAG, "---" + column.column());
                        if (column.isNum()) {
                            value = cursor.getLong(cursor.getColumnIndex(column.column()));
                        } else {
                            value = cursor.getString(cursor.getColumnIndex(column.column()));
                        }
                        setObject(newObject, column.column(), value);
                    }
                }
                result.add(newObject);

                cursor.moveToNext();        //將指標移至下一筆資料
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void setObject(Object object, String name, Object value) {
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

    public static Object getObject(Object object, String name) {
        Field f = null;
        Object obj = null;
        try {
            f = object.getClass().getDeclaredField(name);
            f.setAccessible(true);
            obj = f.get(object);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            f.setAccessible(false);
        }

        return obj;
    }
}
