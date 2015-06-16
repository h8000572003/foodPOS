package com.food.foodpos.util.gcm;

import com.food.foodpos.dto.RestObj;

/**
 * Created by Andy on 2015/6/15.
 */
public interface RestAsyTaskListener<T extends RestObj> {
    void message(RestResultException e, T content);
}
