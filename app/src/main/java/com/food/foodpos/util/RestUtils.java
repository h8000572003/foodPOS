package com.food.foodpos.util;

import android.content.Context;
import android.util.Log;

import com.food.db.domainType.Bill;
import com.food.foodpos.util.gcm.Contract;
import com.food.foodpos.util.gcm.MyNameValuePair;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 2015/6/15.
 */
public class RestUtils {
    private static final String TAG = "RestUtils";

    private static final String UN_BUY_BILL = "/bill/query/unBuy";

    public static String getStringFromUrl(String sonUrl, Context context) {


        final HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 5000);

        DefaultHttpClient demo = new DefaultHttpClient(httpParams);
        demo.getParams().setParameter("http.protocol.content-charset", "UTF-8");

        final String url = String.format(Contract.REST_PATH, Util.getIp(context)) + sonUrl;

        HttpGet httpGet = new HttpGet(url);
        Log.d(TAG, "url=" + url);
        try {

            HttpResponse response = demo.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getStringFromUrl(String sonUrl, List<MyNameValuePair> myNameValuePairList, Context context) {

        final HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 5000);

        DefaultHttpClient demo = new DefaultHttpClient(httpParams);
        demo.getParams().setParameter("http.protocol.content-charset", "UTF-8");

        final String url = String.format(Contract.REST_PATH, Util.getIp(context)) + sonUrl;

        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> pairs = new ArrayList<>();
        for (MyNameValuePair valuePair : myNameValuePairList) {
            pairs.add(new BasicNameValuePair(valuePair.getName(), valuePair.getValue()));
        }


        try {
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
            HttpResponse response = demo.execute(httpPost);
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }


    }



    private class Bills {
        private List<Bill> bills;

        public List<Bill> getBills() {
            return bills;
        }

        public void setBills(List<Bill> bills) {
            this.bills = bills;
        }
    }

    public static <T> T getObjetByJson(Class<T> mClass, String json) {
        return new Gson().fromJson(json, mClass);

    }

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }
}
