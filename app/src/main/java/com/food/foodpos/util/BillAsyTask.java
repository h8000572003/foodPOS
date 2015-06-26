package com.food.foodpos.util;

import android.content.Context;

import com.food.foodpos.dto.JsonBill;
import com.food.foodpos.util.gcm.GenericuRestTask;

/**
 * Created by Andy on 2015/6/16.
 */
public class BillAsyTask extends GenericuRestTask<JsonBill> {


    public BillAsyTask(Context context) {
        super(context);
        setUrl("/bill/query/unBuy/today/noSpeakOut");
    }

    @Override
    protected Class trClass() {
        return JsonBill.class;
    }
}
