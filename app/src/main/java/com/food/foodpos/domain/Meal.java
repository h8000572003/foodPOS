package com.food.foodpos.domain;

/**
 * �\�I
 * Created by 1109001 on 2015/6/2.
 */
public class Meal implements DomainType {
    private Long id; //�Ǹ�
    private Long bill_id; //��Ǹ�
    private String name;// ��ܦW��
    private String spcialize;//�S�O
    private String dolloar; //�浧���B
    private String number;//�ƶq

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBill_id() {
        return bill_id;
    }

    public void setBill_id(Long bill_id) {
        this.bill_id = bill_id;
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
