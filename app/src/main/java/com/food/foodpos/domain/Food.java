package com.food.foodpos.domain;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.food.foodpos.dao.AbstractDAO;

/**
 * ����
 * Created by 1109001 on 2015/6/2.
 */
public class Food implements DomainType {

    private static final String TAG = "Food";
    private Long id; //�Ǹ�
    private String name;//�����W��
    private String dollar;//��~���B




    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDollar() {
        return dollar;
    }

    public void setDollar(String dollar) {
        this.dollar = dollar;
    }


    @Override
    public ContentValues converter() {
        final ContentValues values = new ContentValues();
        values.put("id", this.id);
        values.put("name", this.name);
        values.put("dollar", this.dollar);

        return values;


    }


}

