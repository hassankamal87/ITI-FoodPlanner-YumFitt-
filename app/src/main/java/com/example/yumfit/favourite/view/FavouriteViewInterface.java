package com.example.yumfit.favourite.view;

import com.example.yumfit.pojo.Meal;

import java.util.List;

public interface FavouriteViewInterface {

    void onGetFavouriteMeals(List<Meal> favouriteMeals);

    void deleteMealFromFavourite(Meal meal);
}
