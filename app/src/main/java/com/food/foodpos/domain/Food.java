package com.food.foodpos.domain;

/**
 * ����
 * Created by 1109001 on 2015/6/2.
 */
public class Food implements DomainType{

    private Long id; //�Ǹ�
    private String name;//�����W��
    private String dollar;//��~���B

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDollar() {
        return dollar;
    }

    public void setDollar(String dollar) {
        this.dollar = dollar;
    }
}

