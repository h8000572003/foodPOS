package com.food.foodpos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by Andy on 2015/6/12.
 */
public class GCMFragment extends Fragment {

    private Button sendBtn;
    private Button broadcastBtn;

    public GCMFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addCategory("com.food.foodpos");
        intentFilter.addAction("com.google.android.c2dm.intent.RECEIVE");
        intentFilter.addAction("com.google.android.c2dm.intent.REGISTRATION");

        intentFilter.setPriority(Integer.MAX_VALUE);

        getActivity().registerReceiver(this.mUpdateReceiver, intentFilter);
    }

    @Override
    public void onPause() {

        super.onPause();
        getActivity().unregisterReceiver(mUpdateReceiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gcm_layout, container, false);

        this.sendBtn = (Button) rootView.findViewById(R.id.button);
        this.broadcastBtn = (Button) rootView.findViewById(R.id.broadcast);
        this.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyPost().execute();
            }
        });
//        this.broadcastBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().
//                        sendBroadcast(new Intent("com.google.android.c2dm.intent.RECEIVE"));
//            }
//        });

        return rootView;
    }

    private BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            GCMFragment.this.broadcastReceived(intent);
        }
    };

    public void broadcastReceived(Intent intent) {
        Bundle extras = intent.getExtras();
        String mes = extras.getString("message");
        sendBtn.setText(mes);
    }


    class MyPost extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {


            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("http://192.168.137.100:8080/SpringMVCV1/rest/gcm/send");

            HttpResponse response;
            try {
                response = client.execute(request);

                Log.d("Response of GET request", response.toString());
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }

    ;
}
