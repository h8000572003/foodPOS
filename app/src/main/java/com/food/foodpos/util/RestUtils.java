package com.food.foodpos.util;

import android.util.Log;

import com.food.db.domainType.Bill;
import com.food.db.domainType.State;
import com.google.gson.Gson;

import java.io.BufferedReader;
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
        HttpURLConnection conn = null;
        // Making HTTP request
        try {
            // 建立連線
            URL url = new URL(sonUrl);
            conn = (HttpURLConnection) url.openConnection();
            //===============================
            //下面註解兩行可有可無
            //conn.setReadTimeout(10000);
            //conn.setConnectTimeout(15000);
            //===============================
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            reader.close();
            return sb.toString();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return "";
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
}
