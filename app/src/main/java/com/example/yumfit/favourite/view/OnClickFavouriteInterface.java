package com.example.yumfit.favourite.view;

import com.example.yumfit.pojo.Meal;

public interface OnClickFavouriteInterface {
    void onDeleteBtnClicked(Meal meal);

    void onFavItemClicked(String id);
}
