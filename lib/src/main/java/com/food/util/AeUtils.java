package com.food.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 1109001 on 2015/6/4.
 */
public class AeUtils {
    public static String getNowTime() {
        return new SimpleDateFormat("HHmmss").format(new Date());
    }

    public static String getNowDate() {

        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }


}
