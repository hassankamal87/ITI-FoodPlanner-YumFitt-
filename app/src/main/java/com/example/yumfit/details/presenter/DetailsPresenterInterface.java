package com.example.yumfit.details.presenter;

import com.example.yumfit.network.NetworkDelegate;
import com.example.yumfit.pojo.Meal;

public interface DetailsPresenterInterface {
    void getMealById(String id);
    void insertMealToFavourite(Meal meal);

    void updateDayOfMeal(String id, String day);
}
