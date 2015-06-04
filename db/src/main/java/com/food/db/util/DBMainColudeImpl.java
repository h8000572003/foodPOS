package com.food.db.util;

import com.food.db.domainType.DomainType;

import java.util.List;

/**
 * Created by 1109001 on 2015/6/4.
 */
public class DBMainColudeImpl implements DBMain {
    @Override
    public void insert(DomainType type) {

    }

    @Override
    public void insert(List<DomainType> types) {

    }

    @Override
    public void delete(DomainType type) {

    }

    @Override
    public void delete(List<DomainType> types) {

    }

    @Override
    public void modfiy(DomainType type) {

    }

    @Override
    public void modfiy(List<DomainType> types) {

    }

    @Override
    public <T extends DomainType> List<T> query(Class<T> pClass, String sql, String[] selectionArgs) {
        return null;
    }

    @Override
    public void beginTransaction() {

    }

    @Override
    public void setTransactionSuccessful() {

    }

    @Override
    public void endTransaction() {

    }

    @Override
    public void close() {

    }
}
