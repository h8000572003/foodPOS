package com.food.foodpos.util;

import com.food.foodpos.dto.BaseRestObj;
import com.food.foodpos.util.gcm.GenericuRestTask;

/**
 * Created by Andy on 15/6/24.
 */
public class UpdateIsMealOutTask extends GenericuRestTask<BaseRestObj> {


    public UpdateIsMealOutTask(String txid, String value) {
        setUrl("/bill/update/" + txid + "/isMealOut?value=" + value);
    }

    @Override
    protected Class trClass() {
        return BaseRestObj.class;
    }
}
