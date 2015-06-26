package com.food.foodpos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.food.foodpos.util.GcmRegistrationAsyncTask;
import com.food.foodpos.util.Util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Andy on 2015/6/18.
 */
public class MainFragment extends Fragment {

    private Button orderBtn = null;
    private Button toBtn = null;
    private Button viewBtn;
    private Button restBtn = null;
    private EditText ipEdit;
    private TextView message;
    private ProgressBar progressBar;


    private GcmRegistrationAsyncTask gcmRegistrationAsyncTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu_layout, container, false);
        this.orderBtn
                =
                (Button) rootView.findViewById(R.id.orderBtn);
        this.toBtn = (Button) rootView.findViewById(R.id.toBtn);
        this.viewBtn = (Button) rootView.findViewById(R.id.viewBtn);
        this.restBtn = (Button) rootView.findViewById(R.id.restBtn);
        this.ipEdit = (EditText) rootView.findViewById(R.id.ipEdit);
        this.message = (TextView) rootView.findViewById(R.id.message);
        this.progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);


        this.orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), ToOrderActivity.class);
                startActivity(it);

            }
        });
        this.toBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), ItemListActivity.class);
                startActivity(it);

            }
        });
        this.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), ToViewActivity.class);
                startActivity(it);
            }
        });

        this.setHide();
        this.restBtn.setVisibility(View.GONE);


        this.ipEdit.setText(Util.getIp(getActivity()));

        if (this.gcmRegistrationAsyncTask == null) {
            this.gcmRegistrationAsyncTask = getTask();
            this.gcmRegistrationAsyncTask.execute();


        }
        this.restBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ip = ipEdit.getText().toString();
                if (ip.matches("[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}")) {
                    Util.setIp(ipEdit.getText().toString(), getActivity());
                    gcmRegistrationAsyncTask = getTask();
                    gcmRegistrationAsyncTask.execute();

                } else {
                    Toast.makeText(getActivity(), "請輸入ip格式", Toast.LENGTH_SHORT).show();
                }


            }
        });


        return rootView;
    }

    private GcmRegistrationAsyncTask getTask() {
        final GcmRegistrationAsyncTask task =
                new GcmRegistrationAsyncTask(getActivity()) {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        message.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        ipEdit.setEnabled(false);

                    }

                    @Override
                    protected void onPostExecute(String msg) {
                        if (msg.equals(StringUtils.EMPTY)) {
                            message.setText("連不到伺服器");
                            message.setVisibility(View.VISIBLE);

                            ipEdit.setEnabled(true);
                            MainFragment.this.setHide();
                            MainFragment.this.setShow();
                        } else {
                            restBtn.setVisibility(View.GONE);
                            message.setVisibility(View.GONE);
                            ipEdit.setEnabled(false);

                            toBtn.setVisibility(View.VISIBLE);
                            viewBtn.setVisibility(View.VISIBLE);
                            orderBtn.setVisibility(View.VISIBLE);


                        }
                        progressBar.setVisibility(View.GONE);


                    }
                };
        return task;
    }

    private void setHide() {
        this.toBtn.setVisibility(View.GONE);
        this.viewBtn.setVisibility(View.GONE);
        this.orderBtn.setVisibility(View.GONE);
    }

    private void setShow() {
        this.restBtn.setVisibility(View.VISIBLE);

    }

}
