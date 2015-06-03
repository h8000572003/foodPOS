package com.food.db.domainType;


/**
 * 商品狀態
 * Created by 1109001 on 2015/6/2.
 */
public class State implements DomainType {

    private Long id;//  序號

    private String name;//名稱
    private String extractDollar;//額外金額




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

    public String getExtractDollar() {
        return extractDollar;
    }

    public void setExtractDollar(String extractDollar) {
        this.extractDollar = extractDollar;
    }
}
