package com.food.foodpos.util;

import android.content.Context;
import android.util.Log;

import com.food.db.domainType.Bill;
import com.food.foodpos.util.gcm.Contract;
import com.food.foodpos.util.gcm.MyNameValuePair;
import com.food.foodpos.util.gcm.RestResultException;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;
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


    public static String getStringFromGetUrl(String sonUrl, Context context) {
        return getStringFromGetUrl(sonUrl, null, context);
    }

    public static String getStringFromGetUrl(String sonUrl, List<MyNameValuePair> myNameValuePairList, Context context) {
        final HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 5000);

        DefaultHttpClient demo = new DefaultHttpClient(httpParams);
        demo.getParams().setParameter("http.protocol.content-charset", "UTF-8");

        final StringBuffer urlBuffer = new StringBuffer();
        urlBuffer.append(String.format(Contract.REST_PATH, Util.getIp(context)) + sonUrl);
        if (myNameValuePairList != null && myNameValuePairList.size() > 0) {
            List<String> parmters = new ArrayList<>();
            for (MyNameValuePair valuePair : myNameValuePairList) {
                parmters.add(valuePair.getName() + "=" + valuePair.getValue());
            }
            urlBuffer.append("?" + StringUtils.join(parmters, "&"));
        }
        HttpGet httpGet = new HttpGet(urlBuffer.toString());
        Log.d(TAG, "url=" + urlBuffer.toString());
        try {

            HttpResponse response = demo.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            Log.e(TAG, "e:" + e);

            throw new RestResultException(e);
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
            throw new RestResultException(e);
        }


    }


}
