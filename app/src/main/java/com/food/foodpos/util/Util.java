package com.food.foodpos.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Andy on 15/6/24.
 */
public class Util {

    private static String FILE_KEY = "com.food.pos.FILE_KEY";

    /**
     * 今天使用號碼
     *
     * @param context
     * @return
     */
    public static String getSeq(Context context) {
        final String key = AeUtils.getNowDate() + "useNo";
        final SharedPreferences sharedPreferences =
                context.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE);
        int nowNo = sharedPreferences.getInt(key, 0);
        nowNo = nowNo + 1;
        sharedPreferences.edit().putInt(key, nowNo).commit();
        return String.format("%04d", nowNo);


    }

    public static String getIp(Context context) {
        final SharedPreferences sharedPreferences =
                context.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE);

        return sharedPreferences.getString("ip", "0.0.0.0");
    }

    public static void setIp(String ip, Context context) {
        final SharedPreferences sharedPreferences =
                context.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE);

        sharedPreferences.edit().putString("ip", ip).commit();
    }

    public static void putGCMID(String gcm, Context context) {

        final SharedPreferences sharedPreferences =
                context.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE);

        sharedPreferences.edit().putString("gcm", gcm).commit();


    }

    public static String getGCMID(Context context) {

        final SharedPreferences sharedPreferences =
                context.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE);

        return sharedPreferences.getString("gcm", "");


    }
}

