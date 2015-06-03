package com.food.foodpos.domain;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.food.foodpos.dao.AbstractDAO;

/**
 * 商品狀態
 * Created by 1109001 on 2015/6/2.
 */
public class State implements DomainType {

    private Long id;//  序號
    private Long foodId;//食物序號
    private Long serial;//流水號
    private String name;//名稱
    private String extractDollar;//額外金額

    public static AbstractDAO.DomainConvertzr<State> CONVERTER = new AbstractDAO.DomainConvertzr<State>() {

        public static final String TAG = "AbstractDAO.DomainConvertzr";

        @Override
        public State converter(Cursor cursor) {
            try {
                final State meal = new State();
                meal.setId(cursor.getLong(0));
                meal.setFoodId(cursor.getLong(1));
                meal.setSerial(cursor.getLong(2));
                meal.setName(cursor.getString(3));
                meal.setExtractDollar(cursor.getString(4));
                return meal;
            } catch (Exception e) {
                Log.e(TAG, "Fail to create DomainType");
                throw new RuntimeException("create fail..", e);
            }
        }

    };


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
