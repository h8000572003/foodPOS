package com.food.foodpos.util.gcm;

import android.os.AsyncTask;

import com.food.foodpos.util.RestUtils;
import com.google.gson.Gson;

/**
 * Created by Andy on 2015/6/15.
 */
public class GenericuRestTask extends AsyncTask<Void, Void, String> {

    private RestAsyTaskListener restAsyTaskListener;

    private String url = "";

    public final void setRestAsyTaskListener(RestAsyTaskListener restAsyTaskListener) {
        this.restAsyTaskListener = restAsyTaskListener;
    }

    public final String getUrl() {
        return url;
    }

    public final void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected final String doInBackground(Void... params) {
        return RestUtils.getStringFromUrl(url);
    }

    @Override
    protected final void onPostExecute(String o) {
        final RestObject restObject =
                new Gson().fromJson(o, RestObject.class);
        if (this.restAsyTaskListener != null) {
            if (!restObject.getCode().equals("000")) {
                RestResultException excetion = new RestResultException();
                excetion.setCode(restObject.getCode());
                excetion.setMessage(restObject.getMesaage());
                this.restAsyTaskListener.message(excetion, restObject.getContent());
            } else {
                this.restAsyTaskListener.message(null, restObject.getContent());
            }
        }

    }


}
