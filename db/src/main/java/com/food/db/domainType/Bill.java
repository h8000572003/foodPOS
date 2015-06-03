package com.food.db.domainType;

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

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", orderDate='" + orderDate + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", outOrIn='" + outOrIn + '\'' +
                ", isPaid='" + isPaid + '\'' +
                ", isMealOut='" + isMealOut + '\'' +
                ", dollar='" + dollar + '\'' +
                ", seat='" + seat + '\'' +
                ", feature='" + feature + '\'' +
                '}';
    }
}
