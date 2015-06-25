package com.food.foodpos.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 2015/6/22.
 */
public class OrderDTO {
    private List<OrderAddItem> items = new ArrayList<>();
    private String outOrIn = "I";
    private String useNo = "";
    private String txId = "";
    private String nowTime = "";
    private String nowDate = "";

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getNowDate() {
        return nowDate;
    }

    public void setNowDate(String nowDate) {
        this.nowDate = nowDate;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getUseNo() {

        return useNo;
    }

    public void setUseNo(String useNo) {
        this.useNo = useNo;
    }

    public List<OrderAddItem> getItems() {
        return items;
    }

    public void setItems(List<OrderAddItem> items) {
        this.items = items;
    }


    public String getOutOrIn() {
        return outOrIn;
    }

    public void setOutOrIn(String outOrIn) {
        this.outOrIn = outOrIn;
    }
}
