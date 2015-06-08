package com.food.db.domainType;

/**
 * 餐點
 * Created by 1109001 on 2015/6/2.
 */
@Table(table = "Bill", name = "帳單")
public class Meal implements DomainType {

    @Column(name = "id", isPk = false, column = "id", isNum = true)
    private Long id; //序號

    @Column(name = "txId", isPk = false, column = "txId", isNum = false)
    private String txId;

    @Column(name = "顯示名稱", isPk = false, column = "name", isNum = false)
    private String name;// 顯示名稱

    @Column(name = "特別", isPk = false, column = "spcialize", isNum = false)
    private String spcialize;//特別

    @Column(name = "單筆金額", isPk = false, column = "dolloar", isNum = false)
    private String dolloar; //單筆金額

    @Column(name = "數量", isPk = false, column = "number", isNum = false)
    private String number;//數量

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
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
