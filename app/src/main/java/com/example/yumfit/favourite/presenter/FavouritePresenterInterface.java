package com.example.yumfit.favourite.presenter;

import androidx.lifecycle.LiveData;

import com.example.yumfit.pojo.Meal;

import java.util.List;

public interface FavouritePresenterInterface {
    void getAllMeals();

    void deleteMeal(Meal meal);
}
