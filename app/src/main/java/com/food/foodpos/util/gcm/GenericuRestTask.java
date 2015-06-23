package com.food.foodpos.util.gcm;

import android.os.AsyncTask;

import com.food.foodpos.dto.RestObj;
import com.food.foodpos.util.RestUtils;
import com.google.gson.Gson;

/**
 * Created by Andy on 2015/6/15.
 */
public abstract class GenericuRestTask<T extends RestObj> extends AsyncTask<Void, Void, String> {

    private RestAsyTaskListener restAsyTaskListener;

    protected String url = "";

    public interface RestAsyTaskListener<T extends RestObj> {
        void message(RestResultException e, T content);
    }

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
    protected  String doInBackground(Void... params) {
        return RestUtils.getStringFromUrl(url);
    }

    @Override
    protected final void onPostExecute(String o) {
        final T restObject =
                (T) new Gson().fromJson(o, this.trClass());
        if (this.restAsyTaskListener != null) {

            if (!restObject.getCode().equals("000")) {
                RestResultException excetion = new RestResultException();
                excetion.setCode(restObject.getCode());
                excetion.setMessage(restObject.getMessage());
                this.restAsyTaskListener.message(excetion, restObject);
            } else {
                this.restAsyTaskListener.message(null, restObject);
            }
        }

    }

    protected abstract Class trClass();


}
