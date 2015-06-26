package com.food.foodpos.util;

import android.content.Context;

import com.food.foodpos.dto.JsonBill;
import com.food.foodpos.util.gcm.GenericuRestTask;

/**
 * Created by Andy on 15/6/24.
 */
public class LoadIsSpeakOutBill extends GenericuRestTask<JsonBill> {


    public LoadIsSpeakOutBill(Context context) {
        super(context);
        setUrl("/bill/query/unBuy/today/isSpeak");
    }

    @Override
    protected Class trClass() {
        return JsonBill.class;
    }
}
