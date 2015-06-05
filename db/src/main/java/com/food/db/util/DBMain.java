package com.food.db.util;

import com.food.db.domainType.DomainType;
import com.parse.ParseException;

import java.io.Closeable;
import java.util.List;

/**
 * Created by 1109001 on 2015/6/4.
 */
public interface DBMain {

    void insert(DomainType type);

    void insert(List<DomainType> types);

    void delete(DomainType type);

    void delete(List<DomainType> types);

    void modfiy(DomainType type);

    void modfiy(List<DomainType> types);

    <T extends DomainType> List<T> query(Class<T> pClass, String sql, String[] selectionArgs) ;

    /**
     * 開始交易控管
     */
    void beginTransaction();

    void setTransactionSuccessful();

    void endTransaction();

    void close();


}
