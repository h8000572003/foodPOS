package com.food.foodpos.domain;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.food.foodpos.dao.AbstractDAO;

/**
 * �ӫ~���A
 * Created by 1109001 on 2015/6/2.
 */
public class State implements DomainType {

    private Long id;//  �Ǹ�
    private Long foodId;//�����Ǹ�
    private Long serial;//�y����
    private String name;//�W��
    private String extractDollar;//�B�~���B




    public Long getId() {
        return id;
    }

    @Override
    public ContentValues converter() {
        return null;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public Long getSerial() {
        return serial;
    }

    public void setSerial(Long serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtractDollar() {
        return extractDollar;
    }

    public void setExtractDollar(String extractDollar) {
        this.extractDollar = extractDollar;
    }
}
