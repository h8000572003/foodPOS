package com.food.foodpos.domain;

import android.content.ContentValues;

import com.food.foodpos.dao.AbstractDAO;

import java.io.Serializable;

/**
 * Created by 1109001 on 2015/6/2.
 */
public interface DomainType extends Serializable {
    Long getId();

    ContentValues converter();


}
