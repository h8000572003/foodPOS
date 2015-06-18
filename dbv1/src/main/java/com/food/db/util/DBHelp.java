package com.food.db.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.food.db.R;


/**
 * Created by 1109001 on 2015/6/2.
 */
public class DBHelp extends SQLiteOpenHelper {

    private static final String TAG = "DBHelp";
    private Context context;

    public DBHelp(Context context) {
        super(context, "db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(context.getString(R.string.create_bill_sql));
        db.execSQL(context.getString(R.string.create_food_sql));
        db.execSQL(context.getString(R.string.create_meal_sql));
        db.execSQL(context.getString(R.string.create_state_sql));
        this.insertDefault(db);
    }

    private void insertDefault(SQLiteDatabase db) {

        for (String sql : context.getResources().getStringArray(R.array.inti_sql)) {
            Log.i(TAG, sql);
            db.execSQL(sql);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);
    }

    public void onDropTable(SQLiteDatabase db) {

    }
}
