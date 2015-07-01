package com.food.foodpos.util;

import android.content.Context;

import com.food.foodpos.dto.JsonBill;
import com.food.foodpos.util.gcm.GenericuRestTask;

/**
 * 取得今天尚未叫食物
 * Created by Andy on 2015/6/16.
 */
public class BillIsNoSpeakOutTask extends GenericuRestTask<JsonBill> {


    public BillIsNoSpeakOutTask(Context context) {
        super(context);
        setUrl("/bill/query/unBuy/today/noSpeakOut");
    }

    @Override
    protected Class trClass() {
        return JsonBill.class;
    }
}
