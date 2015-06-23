package com.food.foodpos.util.gcm;

import com.food.foodpos.dto.RestObj;
import com.food.foodpos.util.RestUtils;

import java.util.List;

/**
 * Created by Andy on 15/6/23.
 */
public abstract class GenericuPostRestTask<T extends RestObj> extends GenericuRestTask<T> {

    private List<MyNameValuePair> pairs = null;


    @Override
    protected String doInBackground(Void... params) {
        return RestUtils.getStringFromUrl(url, pairs);
    }


    public GenericuPostRestTask(String url) {
        this.url = url;


    }

    public List<MyNameValuePair> getPairs() {
        return pairs;
    }

    public void setPairs(List<MyNameValuePair> pairs) {
        this.pairs = pairs;
    }
}