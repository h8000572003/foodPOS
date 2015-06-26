package com.food.foodpos.util;

import android.content.Context;

import com.food.foodpos.dto.BaseRestObj;
import com.food.foodpos.dto.BillSon;
import com.food.foodpos.util.gcm.GenericuPostRestTask;
import com.food.foodpos.util.gcm.MyNameValuePair;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 15/6/23.
 */
public class FoodUploadAsyTask extends GenericuPostRestTask<BaseRestObj> {

    private static final String URL = "/bill/insert/newOne";
    private BillSon son;

    public FoodUploadAsyTask(Context context) {
        super(URL,context);
    }

    @Override
    protected Class trClass() {
        return BaseRestObj.class;
    }

    public void setSon(BillSon son) {
        this.son = son;


        final List<MyNameValuePair> pairs = new ArrayList<>();

        MyNameValuePair pair = new MyNameValuePair();

        String message = new Gson().toJson(son);

        try {
            message = URLDecoder.decode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        pair.setName("message");
        pair.setValue(message);
        pairs.add(pair);


        setPairs(pairs);
    }
}
