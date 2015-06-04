package com.food.db.domainType;

/**
 * Created by 1109001 on 2015/6/2.
 */
@Table(table = "Bill", name = "帳單")
public class Bill implements DomainType {


    @Column(name = "id", isPk = false, column = "id", isNum = true)
    private Long id;//

    @Column(name = "建立日期", isPk = false, column = "orderDate", isNum = false)
    private String orderDate;//  建立日期

    @Column(name = "建立日期", isPk = false, column = "orderTime", isNum = false)
    private String orderTime;// 建立時間

    @Column(name = "內用或外帶", isPk = false, column = "outOrIn", isNum = false)
    private String outOrIn;//內用或外帶

    @Column(name = "已結帳", isPk = false, column = "isPaid", isNum = false)
    private String isPaid;//已結帳

    @Column(name = "已出菜", isPk = false, column = "isMealOut", isNum = false)
    private String isMealOut;//已出菜

    @Column(name = "總金額", isPk = false, column = "dollar", isNum = false)
    private String dollar;//總金額

    @Column(name = "座位", isPk = false, column = "seat", isNum = false)
    private String seat;//座位

    @Column(name = "特徵", isPk = false, column = "feature", isNum = false)
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
