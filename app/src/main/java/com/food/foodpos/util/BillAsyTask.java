package com.food.foodpos.util;

import com.food.foodpos.dto.BillSon;
import com.food.foodpos.dto.JsonBill;
import com.food.foodpos.util.gcm.GenericuRestTask;

import java.util.List;

/**
 * Created by Andy on 2015/6/16.
 */
public class BillAsyTask extends GenericuRestTask<JsonBill> {
    @Override
    protected Class trClass() {
        return JsonBill.class;
    }
}
