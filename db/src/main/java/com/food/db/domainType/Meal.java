package com.food.db.domainType;

/**
 * 餐點
 * Created by 1109001 on 2015/6/2.
 */
public class Meal implements DomainType {
    private Long id; //序號
    private Long billId; //單序號
    private String name;// 顯示名稱
    private String spcialize;//特別
    private String dolloar; //單筆金額
    private String number;//數量

    public Long getId() {
        return id;
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
