package com.food.foodpos.domain;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.food.foodpos.dao.AbstractDAO;

/**
 * �\�I
 * Created by 1109001 on 2015/6/2.
 */
public class Meal implements DomainType {
    private Long id; //�Ǹ�
    private Long billId; //��Ǹ�
    private String name;// ��ܦW��
    private String spcialize;//�S�O
    private String dolloar; //�浧���B
    private String number;//�ƶq

    public Long getId() {
        return id;
    }

    public static AbstractDAO.DomainConvertzr<Meal> CONVERTER = new AbstractDAO.DomainConvertzr<Meal>() {

        public static final String TAG = "AbstractDAO.DomainConvertzr";

        @Override
        public Meal converter(Cursor cursor) {
            try {
                final Meal meal = new Meal();
                meal.setId(cursor.getLong(0));
                meal.setBillId(cursor.getLong(1));
                meal.setName(cursor.getString(2));
                meal.setSpcialize(cursor.getString(3));
                meal.setDolloar(cursor.getString(4));
                meal.setNumber(cursor.getString(5));
                return meal;
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
        values.put("billId", this.billId);
        values.put("name", this.name);
        values.put("spcialize", this.spcialize);
        values.put("dolloar", this.dolloar);
        values.put("number", this.number);


        return values;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpcialize() {
        return spcialize;
    }

    public void setSpcialize(String spcialize) {
        this.spcialize = spcialize;
    }

    public String getDolloar() {
        return dolloar;
    }

    public void setDolloar(String dolloar) {
        this.dolloar = dolloar;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
