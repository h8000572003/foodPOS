package com.food.foodpos.service;

import android.content.Context;

import com.food.foodpos.dto.BillSon;
import com.food.foodpos.dto.ItemListDTO;

import java.util.List;

/**
 * Created by Andy on 15/6/25.
 */
public interface ToFoodService {
    void insertToWork(ItemListDTO dto, Context context);

    List<BillSon> queryNowWorkList(ItemListDTO dto, Context context);

    void toFood(ItemListDTO dto, Context context);

    void toClose(ItemListDTO dto, Context context);


    void reduceNum(ItemListDTO dto, Context context);
}
