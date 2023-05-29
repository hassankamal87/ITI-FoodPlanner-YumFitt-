package com.example.yumfit.daydetail.view;

import com.example.yumfit.pojo.Meal;

public interface OnDayClickInterface {
    void onDeleteBtnClicked(Meal meal);

    void onFavItemClicked(String id);
}
