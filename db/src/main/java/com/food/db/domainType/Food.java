package com.food.db.domainType;


/**
 * 食物
 * Created by 1109001 on 2015/6/2.
 */
@Table(table = "Food", name = "食物")
public class Food implements DomainType {


    @Column(name = "序號", isPk = false, column = "id", isNum = true)
    private Long id; //序號


    @Column(name = "食物名稱", isPk = false, column = "name", isNum = true)
    private String name;//食物名稱

    @Column(name = "單品金額", isPk = false, column = "dollar", isNum = true)
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

