package com.food.foodpos;

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


        return rootView;
    }
}
