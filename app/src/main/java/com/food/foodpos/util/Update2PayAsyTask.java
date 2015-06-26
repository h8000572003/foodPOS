package com.food.foodpos.util;

import android.content.Context;

import com.food.foodpos.dto.BaseRestObj;
import com.food.foodpos.util.gcm.GenericuRestTask;

/**
 * Created by Andy on 2015/6/17.
 */
public class Update2PayAsyTask extends GenericuRestTask<BaseRestObj> {


    public Update2PayAsyTask(String txId,Context context) {
        super(context);
        final String url = "/bill/update/" + txId + "/isPay";
        this.setUrl(url);
    }

    @Override
    protected Class trClass() {
        return BaseRestObj.class;
    }
}
