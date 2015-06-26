package com.food.foodpos.service;

import android.content.Context;
import android.util.Log;

import com.food.db.domainType.Bill;
import com.food.db.domainType.Meal;
import com.food.db.util.DBFactory;
import com.food.db.util.DBMain;
import com.food.foodpos.dto.BillSon;
import com.food.foodpos.dto.ItemListDTO;
import com.food.foodpos.util.UpdateIsMealOutTask;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 15/6/25.
 */
public class ToFoodServiceImpl implements ToFoodService {
    private static final String TAG = "ToFoodServiceImpl";
    private static ToFoodService service;

    public static ToFoodService get() {
        if (service == null) {
            service = new ToFoodServiceImpl();
        }

        return service;
    }

    @Override
    public void insertToWork(ItemListDTO dto, Context context) {
        DBMain dbMain = DBFactory.getLocationDB(context);
        try {
            dbMain.beginTransaction();

            final Bill bill = dto.getNowSelectBill().getBill();
            final List<Meal> meals = dto.getNowSelectBill().getMeals();


            dbMain.insert(bill);

            for (Meal meal : meals) {
                dbMain.insert(meal);
            }

            dbMain.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            dbMain.endTransaction();
            dbMain.close();
        }

    }


    @Override
    public List<BillSon> queryNowWorkList(ItemListDTO dto, Context context) {
        DBMain dbMain = DBFactory.getLocationDB(context);
        try {

            final List<BillSon> billJsons = new ArrayList<BillSon>();
            final List<Bill> bills =
                    dbMain.query(Bill.class, "select * from bill", new String[]{});

            for (Bill bill : bills) {
                BillSon json = new BillSon();

                List<Meal> meals =
                        dbMain.query(Meal.class, "select * from meal where txId=?", new String[]{bill.getTxId()});
                json.setBill(bill);
                json.setMeals(meals);

                billJsons.add(json);
            }

            return billJsons;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            dbMain.close();
        }
        return new ArrayList<BillSon>();
    }

    @Override
    public void toFood(ItemListDTO dto, Context context) {
        DBMain dbMain =
                DBFactory.getLocationDB(context);
        try {
            dbMain.beginTransaction();
            modfiyMealUseNo(dbMain, dto.getNowMeal());
            modfiyBillForTotal(dbMain, dto, dto.getNowSelectBill(),context);


            dbMain.setTransactionSuccessful();
        } finally {
            dbMain.endTransaction();
            dbMain.close();

        }
    }

    @Override
    public void toClose(ItemListDTO dto, Context context) {

        BillSon json =
                dto.getNowSelectBill();


        DBMain dbMain =
                DBFactory.getLocationDB(context);
        try {
            dbMain.beginTransaction();
            dbMain.delete(json.getBill());

            for (Meal meal : json.getMeals()) {
                dbMain.delete(meal);
            }

            dto.getAddList().remove(json);

            dbMain.setTransactionSuccessful();
        } finally {
            dbMain.endTransaction();
            dbMain.close();

        }

    }

    @Override
    public void reduceNum(ItemListDTO dto, Context context) {

        final Meal nowMeal = dto.getNowMeal();
        if (StringUtils.equals(nowMeal.getUseNumber(), nowMeal.getNumber())) {
            return;
        }

        DBMain dbMain =
                DBFactory.getLocationDB(context);
        try {
            dbMain.beginTransaction();

            int useNo = Integer.parseInt(dto.getNowMeal().getUseNumber());
            useNo += 1;
            nowMeal.setUseNumber(useNo + "");
            dbMain.modfiy(nowMeal);

            if (this.isBillMealOut(dto.getNowSelectBill().getMeals())) {//都出菜完
                dto.getNowSelectBill().getBill().setIsMealOut("Y");
                dbMain.modfiy(dto.getNowSelectBill().getBill());
                new UpdateIsMealOutTask(dto.getNowSelectBill().getBill().getTxId(), "Y",context).execute();//出菜狀況改完，Y
            }


            dbMain.setTransactionSuccessful();
        } finally {
            dbMain.endTransaction();
            dbMain.close();

        }
    }

    private void modfiyMealUseNo(DBMain dbMain, Meal meal) {
        meal.setUseNumber(meal.getNumber());
        dbMain.modfiy(meal);
    }


    private void modfiyBillForTotal(DBMain dbMain, ItemListDTO dto, BillSon son,Context context) {


        final Bill bill = son.getBill();

        if (this.isBillMealOut(son.getMeals())) {//都出菜完

            bill.setIsMealOut("Y");
            dbMain.modfiy(bill);
            new UpdateIsMealOutTask(son.getBill().getTxId(), "Y",context).execute();//出菜狀況改完，Y


        }

    }


    /**
     * 是否所有菜都出完
     *
     * @param meals
     * @return
     */
    private boolean isBillMealOut(List<Meal> meals) {

        for (Meal meal : meals) {
            if (!StringUtils.equals(meal.getUseNumber(), meal.getNumber())) {
                return false;
            }

        }
        return true;
    }

    /**
     * 是否已經出菜且結帳
     *
     * @param bill
     * @return
     */
    private boolean checkIsMealOutAndPay(Bill bill) {
        boolean isMealOut = StringUtils.equals(bill.getIsMealOut(), "Y");
        boolean isPaid = StringUtils.equals(bill.getIsPaid(), "Y");
        if (isMealOut && isPaid) {
            return true;
        } else {
            return false;
        }
    }

}
