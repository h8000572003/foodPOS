package com.food.foodpos.util.gcm;

/**
 * Created by Andy on 2015/6/15.
 */
public interface RestAsyTaskListener {
    void message(RestResultException e, String content);
}
