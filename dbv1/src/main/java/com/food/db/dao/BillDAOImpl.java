package com.food.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.food.db.domainType.Bill;


/**
 * Created by 1109001 on 2015/6/2.
 */
public class BillDAOImpl extends AbstractDAO<Bill> {
    public BillDAOImpl(Context context, SQLiteDatabase db) {
        super(Bill.class, db);
    }

    public static AbstractDAO.DomainConverter<Bill> CONVERTER = new DomainConverter<Bill>() {

        public static final String TAG = "AbstractDAO.DomainConvertzr<Bill>";

        @Override
        public Bill converter(Cursor cursor) {
            try {
                final Bill bill = new Bill();
                bill.setTxId(cursor.getString(0));
                bill.setOrderDate(cursor.getString(1));
                bill.setOrderTime(cursor.getString(2));
                bill.setOutOrIn(cursor.getString(3));
                bill.setIsPaid(cursor.getString(4));
                bill.setIsMealOut(cursor.getString(5));
                bill.setDollar(cursor.getString(6));
                bill.setSeat(cursor.getString(7));
                bill.setFeature(cursor.getString(8));
                bill.setIsSpeakOut(cursor.getString(9));

                return bill;
            } catch (Exception e) {
                Log.e(TAG, "Fail to create DomainType");
                throw new RuntimeException("create fail..", e);
            }
        }

        @Override
        public ContentValues converter(Bill domainType) {
            final ContentValues values = new ContentValues();
            values.put("txId", domainType.getTxId());
            values.put("orderDate", domainType.getOrderDate());
            values.put("orderTime", domainType.getOrderTime());
            values.put("outOrIn", domainType.getOutOrIn());
            values.put("isPaid", domainType.getIsPaid());
            values.put("isMealOut", domainType.getIsMealOut());
            values.put("dollar", domainType.getDollar());
            values.put("seat", domainType.getSeat());
            values.put("feature", domainType.getFeature());
            values.put("isSpeakOut", domainType.getIsSpeakOut());
            return values;
        }

    };

    @Override
    protected DomainConverter getDomainConvertzr() {
        return BillDAOImpl.CONVERTER;
    }


    @Override
    protected String[] getAllColumns() {
        return new String[]{"txId", "orderDate", "orderTime", "outOrIn"
                , "isPaid", "isMealOut", "dollar", "seat", "feature"};
    }

    @Override
    protected String getTableName() {
        return "Bill";
    }
}
