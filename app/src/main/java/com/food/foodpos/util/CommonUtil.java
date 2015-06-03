package com.food.foodpos.util;

import android.util.Log;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by 1109001 on 2015/6/3.
 */
public class CommonUtil {
    private static final String TAG = "CommonUtil";

    public static void close(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            Log.e(TAG, String.valueOf(e));
        }

    }
}
