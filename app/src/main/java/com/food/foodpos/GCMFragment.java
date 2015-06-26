package com.food.foodpos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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




    ;
}
