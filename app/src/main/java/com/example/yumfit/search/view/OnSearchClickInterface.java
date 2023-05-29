package com.example.yumfit.search.view;

import com.example.yumfit.pojo.Meal;

public interface OnSearchClickInterface {
    void onSaveBtnClicked(Meal meal);

    void onItemClicked(String id);
}
