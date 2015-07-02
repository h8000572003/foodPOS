package com.food.foodpos.util;

import android.content.Context;

import com.food.foodpos.dto.JsonBill;
import com.food.foodpos.util.gcm.GenericuRestTask;

/**
 * 取得今天尚未叫食物
 * Created by Andy on 2015/6/16.
 */
public class BillIsNoSpeakOutAsyTask extends GenericuRestTask<JsonBill> {

    private String URL = "/bill/query/unBuy/today/noSpeakOut";

    public BillIsNoSpeakOutAsyTask(Context context) {
        super(context);
        setUrl(URL);
    }

    @Override
    protected Class trClass() {
        return JsonBill.class;
    }
}
