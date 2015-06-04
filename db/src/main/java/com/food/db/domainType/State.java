package com.food.db.domainType;


/**
 * 商品狀態
 * Created by 1109001 on 2015/6/2.
 */
@Table(table = "Bill", name = "帳單")
public class State implements DomainType {


    @Column(name = "id", isPk = false, column = "id", isNum = true)
    private Long id;//  序號

    @Column(name = "foodId", isPk = false, column = "foodId", isNum = true)
    private Long foodId;

    @Column(name = "名稱", isPk = false, column = "name", isNum = false)
    private String name;//名稱

    @Column(name = "額外金額", isPk = false, column = "extractDollar", isNum = false)
    private String extractDollar;//額外金額


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
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
