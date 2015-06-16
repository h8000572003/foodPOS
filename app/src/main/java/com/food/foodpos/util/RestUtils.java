package com.food.foodpos.util;

import android.util.Log;

import com.food.db.domainType.Bill;
import com.food.foodpos.util.gcm.Contract;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

        HttpGet httpGet = new HttpGet(Contract.REST_ROOT_URL+sonUrl);

        try {
            HttpResponse response = demo.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
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
