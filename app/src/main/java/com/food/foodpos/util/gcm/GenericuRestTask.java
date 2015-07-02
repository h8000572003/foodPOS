package com.food.foodpos.util.gcm;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.food.foodpos.dto.RestObj;
import com.food.foodpos.util.RSCDMSG;
import com.food.foodpos.util.RestUtils;
import com.google.gson.Gson;

/**
 * Created by Andy on 2015/6/15.
 */
public abstract class GenericuRestTask<T extends RestObj> extends AsyncTask<Void, Void, String> {

    private static final String TAG = "GenericuRestTask";
    private RestAsyTaskListener restAsyTaskListener;
    protected Context context;

    protected String url = "";

    private static final String GOOD_CODE = "000";

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


    public GenericuRestTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {

        try {
            return RestUtils.getStringFromGetUrl(url, context);
        } catch (RestResultException e) {
            this.whenException(e);
            throw e;
        } catch (Exception e) {

            throw new RestResultException(e);

        }

    }

    public void whenException(RestResultException e) {
        Log.i(TAG, "doNothing..");
    }


    @Override
    protected final void onPostExecute(String o) {
        Log.d(TAG, "json=" + o);


        final T restObject =
                (T) new Gson().fromJson(o, this.trClass());


        if (this.restAsyTaskListener != null) {

            if (!restObject.getCode().equals(GOOD_CODE)) {
                RSCDMSG resdcode = RSCDMSG.lookUp(restObject.getCode());
                RestResultException excetion = new RestResultException(resdcode, restObject.getMessage());

                this.restAsyTaskListener.message(excetion, restObject);
            } else {
                this.restAsyTaskListener.message(null, restObject);
            }
        }


    }

    protected abstract Class trClass();


}
