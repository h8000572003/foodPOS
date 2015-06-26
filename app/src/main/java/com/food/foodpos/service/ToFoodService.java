package com.food.foodpos.service;

import android.content.Context;

import com.food.foodpos.dto.BillSon;
import com.food.foodpos.dto.ItemListDTO;

import java.util.List;

/**
 * 出餐服務
 * Created by Andy on 15/6/25.
 */
public interface ToFoodService {
    /**
     * 新增帳單
     * @param dto
     * @param context
     */
    void insertToWork(ItemListDTO dto, Context context);

    /**
     * 取得處理中本機餐點資料
     * @param dto
     * @param context
     * @return
     */
    List<BillSon> queryNowWorkList(ItemListDTO dto, Context context);

    /**
     * 全部出餐
     * @param dto
     * @param context
     */
    void toFood(ItemListDTO dto, Context context);

    /**
     * 帳單結案
     * @param dto
     * @param context
     */
    void toClose(ItemListDTO dto, Context context);

    /**
     * 出餐數量減1
     * @param dto
     * @param context
     */
    void reduceNum(ItemListDTO dto, Context context);
}
