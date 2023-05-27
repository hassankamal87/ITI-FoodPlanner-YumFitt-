package com.example.yumfit.details.view;

import com.example.yumfit.pojo.Meal;

import java.util.List;

public interface DetailsViewInterface {
    void onGetMealDetails(List<Meal> meals);

    void insertMealToFavourite(Meal meal);
    void onFailToGetMealDetails(String message);
}
