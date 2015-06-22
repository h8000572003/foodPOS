package com.food.foodpos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Andy on 2015/6/18.
 */
public class MainFragment extends Fragment {

    private Button orderBtn = null;
    private Button toBtn = null;
    private Button viewBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu_layout, container, false);
        this.orderBtn
                =
                (Button) rootView.findViewById(R.id.orderBtn);
        this.toBtn = (Button) rootView.findViewById(R.id.toBtn);
        this.viewBtn = (Button) rootView.findViewById(R.id.viewBtn);

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
                Intent it = new Intent(getActivity(), ToOrderActivity.class);
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


        return rootView;
    }

}
