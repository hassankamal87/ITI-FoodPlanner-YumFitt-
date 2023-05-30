package com.example.yumfit.profile.presenter;

import androidx.lifecycle.LiveData;

import com.example.yumfit.pojo.Meal;

import java.util.List;

public interface ProfilePresenterInterface {
    void deleteAllFavouriteMeals();

    void getAllFavouriteMeals();
}
