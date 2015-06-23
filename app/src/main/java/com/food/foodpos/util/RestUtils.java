package com.food.foodpos.util;

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

    public static String getStringFromUrl(String sonUrl) {
        DefaultHttpClient demo = new DefaultHttpClient();
        demo.getParams().setParameter("http.protocol.content-charset", "UTF-8");

        HttpGet httpGet = new HttpGet(Contract.REST_ROOT_URL + sonUrl);

        try {
            HttpResponse response = demo.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getStringFromUrl(String sonUrl, List<MyNameValuePair> myNameValuePairList) {

        DefaultHttpClient demo = new DefaultHttpClient();
        demo.getParams().setParameter("http.protocol.content-charset", "UTF-8");


        HttpPost httpPost = new HttpPost(Contract.REST_ROOT_URL + sonUrl);
        List<NameValuePair> pairs = new ArrayList<>();
        for (MyNameValuePair valuePair : myNameValuePairList) {
            pairs.add(new BasicNameValuePair(valuePair.getName(), valuePair.getValue()));
        }



        try {
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
            HttpResponse response = demo.execute(httpPost);
            return EntityUtils.toString(response.getEntity(),"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }


    }


    public static List<Bill> getUnBuyBillsList() {

        return new Gson().fromJson(getStringFromUrl(UN_BUY_BILL), Bills.class).getBills();

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
