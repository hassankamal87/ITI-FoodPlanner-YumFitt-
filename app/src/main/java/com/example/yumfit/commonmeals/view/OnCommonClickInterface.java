package com.example.yumfit.commonmeals.view;

import com.example.yumfit.pojo.Meal;

public interface OnCommonClickInterface {
    void onSaveBtnClicked(Meal meal);

    void onMealItemClicked(String id);
}
