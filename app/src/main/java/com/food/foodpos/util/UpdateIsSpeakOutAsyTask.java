package com.food.foodpos.util;

import android.content.Context;

import com.food.foodpos.dto.BaseRestObj;
import com.food.foodpos.util.gcm.GenericuRestTask;

/**
 * Created by Andy on 15/6/24.
 */
public class UpdateIsSpeakOutAsyTask extends GenericuRestTask<BaseRestObj> {


    public UpdateIsSpeakOutAsyTask(String txid, String value,Context context) {
        super(context);
        setUrl("/bill/update/" + txid + "/isSpeakOut?value=" + value);
    }

    @Override
    protected Class trClass() {
        return BaseRestObj.class;
    }
}
