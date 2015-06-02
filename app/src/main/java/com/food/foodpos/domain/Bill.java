package com.food.foodpos.domain;

import java.io.Serializable;

/**
 * �b��
 * Created by 1109001 on 2015/6/2.
 */
public class Bill implements DomainType {

    private Long id;//id
    private String orderDate;//  �إߤ��
    private String orderTime;// �إ߮ɶ�
    private String outOrIn;//���ΩΥ~�a
    private String isPaid;//�w���b
    private String isMealOut;//�w�X��
    private String dollar;//�`���B
    private String seat;//�y��
    private String feature;//�S�x

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
