package com.food.foodpos.domain;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.food.foodpos.dao.AbstractDAO;

import java.io.Serializable;

import static com.food.foodpos.dao.AbstractDAO.*;

/**
 * 帳單
 * Created by 1109001 on 2015/6/2.
 */
public class Bill implements DomainType {

    private static final String TAG = "Bill";
    private Long id;//id
    private String orderDate;//  建立日期
    private String orderTime;// 建立時間
    private String outOrIn;//內用或外帶
    private String isPaid;//已結帳
    private String isMealOut;//已出菜
    private String dollar;//總金額
    private String seat;//座位
    private String feature;//特徵

    public static AbstractDAO.DomainConvertzr<Bill> CONVERTER = new DomainConvertzr<Bill>() {

        @Override
        public Bill converter(Cursor cursor) {
            try {
                final Bill bill = new Bill();
                bill.setId(cursor.getLong(0));
                bill.setOrderDate(cursor.getString(1));
                bill.setOrderTime(cursor.getString(2));
                bill.setOutOrIn(cursor.getString(3));
                bill.setIsPaid(cursor.getString(4));
                bill.setIsMealOut(cursor.getString(5));
                bill.setDollar(cursor.getString(6));
                bill.setSeat(cursor.getString(7));
                bill.setFeature(cursor.getString(8));
                return bill;
            } catch (Exception e) {
                Log.e(TAG, "Fail to create DomainType");
                throw new RuntimeException("create fail..", e);
            }
        }

    };

    @Override
    public ContentValues converter() {
        final ContentValues values = new ContentValues();
        values.put("id", this.id);
        values.put("orderDate", this.orderDate);
        values.put("orderTime", this.orderTime);
        values.put("outOrIn", this.outOrIn);
        values.put("isPaid", this.isPaid);
        values.put("isMealOut", this.isMealOut);
        values.put("dollar", this.dollar);
        values.put("seat", this.seat);
        values.put("feature", this.feature);
        return values;
    }




    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOutOrIn() {
        return outOrIn;
    }

    public void setOutOrIn(String outOrIn) {
        this.outOrIn = outOrIn;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getIsMealOut() {
        return isMealOut;
    }

    public void setIsMealOut(String isMealOut) {
        this.isMealOut = isMealOut;
    }

    public String getDollar() {
        return dollar;
    }

    public void setDollar(String dollar) {
        this.dollar = dollar;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }


}
