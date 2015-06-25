package com.food.foodpos.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.food.foodpos.util.gcm.Contract;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.commons.lang3.StringUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by 1109001 on 2015/6/10.
 */
public class GcmRegistrationAsyncTask extends AsyncTask<Void, Void, String> {

    private GoogleCloudMessaging gcm;
    private Context context;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = Contract.SENDER_ID;

    public GcmRegistrationAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {


        String regId = Util.getGCMID(context);

        if (StringUtils.isNotBlank(regId)) {
            return regId;
        }

        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(context);
            }

            regId = gcm.register(SENDER_ID);
            Util.putGCMID(regId, context);

            RestUtils.getStringFromUrl("/gcm/insert?id=" + regId);
        } catch (Exception e) {
            Log.e("E:", e.getMessage());
        } finally {
            return regId;

        }


    }


    @Override
    protected void onPostExecute(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        Logger.getLogger("REGISTRATION").log(Level.INFO, msg);
    }
}