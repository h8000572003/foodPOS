package com.food.foodpos.util;

/**
 * Created by Andy on 2015/6/22.
 */
public enum Food {


    f1("排骨意麵", 65),//
    f2("大排骨意麵", 80),//
    f3("排骨河粉", 65),//
    f4("大排骨河粉", 80),//
    f5("排骨冬粉", 65),//
    f6("大排骨冬粉", 80),//
    f7("大排骨泡飯", 65),//
    f8("大排骨泡飯", 80),//

    f9("招牌肉粽", 40),//
    f10("香菇蛋黃", 55),//
    f11("排骨酥湯", 50),//
    f12("貢丸湯", 25),//
    f13("蘿蔔湯", 30),//
    f14("燙青菜", 30),//
    f15("小菜", 30),//
    f16("黑白切", 49),//
    f17("滷肉飯", 25),//
    f18("控肉販", 65),//
    f19("咖哩飯", 75),//
    f20("米糕", 30),//
    f21("碗粿", 30),//
    f22("乾意麵", 35),//
    f23("大乾意麵", 50),//
    f24("乾河粉", 35),//
    f25("大乾河粉", 50),//
    f26("乾冬粉", 35),//
    f27("大乾冬粉", 50),//
    f28("冬瓜茶", 15),//
    //

    ;
    private String name = "";
    private int dollar = 0;

    Food(String name, int dollar) {
        this.name = name;
        this.dollar = dollar;
    }

    public String getName() {
        return name;
    }

    public int getDollar() {
        return dollar;
    }
}
