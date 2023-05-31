package com.example.yumfit.home.view;

import com.example.yumfit.pojo.Category;
import com.example.yumfit.pojo.Country;
import com.example.yumfit.pojo.Meal;

public interface OnClickInterface {
    void onSaveBtnClick(Meal meal);

    void onDailyInspirationItemClicked(String id);

    void onCountryItemClicked(Country country);

    void onCategoryItemClicked(Category category);
}
