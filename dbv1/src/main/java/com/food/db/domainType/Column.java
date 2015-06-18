package com.food.db.domainType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 1109001 on 2015/6/3.
 */

@Target(value = {ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    String column();

    String name();

    boolean isPk();

    boolean isNum();


}
