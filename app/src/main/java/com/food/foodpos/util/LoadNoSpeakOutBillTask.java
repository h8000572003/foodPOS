package com.food.foodpos.util;

import com.food.foodpos.dto.JsonBill;
import com.food.foodpos.util.gcm.GenericuRestTask;

/**
 * Created by Andy on 15/6/25.
 */
public class LoadNoSpeakOutBillTask extends GenericuRestTask<JsonBill> {


    public LoadNoSpeakOutBillTask() {
        setUrl("/bill/query/unBuy/today/noSpeakOut");
    }

    @Override
    protected Class trClass() {
        return JsonBill.class;
    }
}
