package com.food.foodpos.util;

import com.food.foodpos.dto.JsonBill;
import com.food.foodpos.util.gcm.GenericuRestTask;

/**
 * Created by Andy on 2015/6/16.
 */
public class BillAsyTask extends GenericuRestTask<JsonBill> {


    public BillAsyTask() {
        setUrl("/bill/query/unBuy/today/noSpeakOut");
    }

    @Override
    protected Class trClass() {
        return JsonBill.class;
    }
}
