package com.food.db.domainType;


/**
 * 食物
 * Created by 1109001 on 2015/6/2.
 */
public class Food implements DomainType {

    private static final String TAG = "Food";
    private Long id; //序號
    private String name;//食物名稱
    private String dollar;//單品金額




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

