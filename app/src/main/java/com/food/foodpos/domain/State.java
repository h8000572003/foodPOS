package com.food.foodpos.domain;

/**
 * �ӫ~���A
 * Created by 1109001 on 2015/6/2.
 */
public class State implements DomainType {

    private Long id;//  �Ǹ�
    private Long food_id;//�����Ǹ�
    private Long serial;//�y����
    private String name;//�W��
    private String extractDollar;//�B�~���B

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFood_id() {
        return food_id;
    }

    public void setFood_id(Long food_id) {
        this.food_id = food_id;
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
