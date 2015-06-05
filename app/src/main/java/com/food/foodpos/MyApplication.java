package com.food.foodpos;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by 1109001 on 2015/6/3.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "22IjQiE5OTa6otvH93T8zZijDuB2Mdq9xYudN9zl", "GjlRYV1HZnnO0eE8uOAVsy0OaRssRoqZRmY7CoRz");

    }
}
