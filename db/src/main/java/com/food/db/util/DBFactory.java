package com.food.db.util;

import android.content.Context;

/**
 * Created by 1109001 on 2015/6/4.
 */
public class DBFactory {
    public static synchronized DBMain getLocationDB(Context context) {
        final DBHelp dbHelp = new DBHelp(context);
        DBMinLocationImpl dbmin = new DBMinLocationImpl();
        dbmin.setDb(dbHelp.getWritableDatabase());
        return dbmin;

    }

    public static synchronized DBMain getColudeDB(Context context) {
        DBMain dbMain = new DBMainColudeImpl();
        return dbMain;
    }
}
