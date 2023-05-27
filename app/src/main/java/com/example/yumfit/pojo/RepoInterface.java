package com.example.yumfit.pojo;

import androidx.lifecycle.LiveData;

import com.example.yumfit.network.NetworkDelegate;

import java.util.List;

public interface RepoInterface {
    //Retrofit
    void getMealByName(String name, NetworkDelegate networkDelegate);

    void getMealByFirstChar(String firstChar, NetworkDelegate networkDelegate);

    void getMealById(String id, NetworkDelegate networkDelegate);

    void getRandomMeal(NetworkDelegate networkDelegate);

    void getAllCategories(NetworkDelegate networkDelegate);

    void getAllCountries(NetworkDelegate networkDelegate);

    void getAllIngredient(NetworkDelegate networkDelegate);

    void getMealsByIngredient(String ingredient, NetworkDelegate networkDelegate);

    void getMealsByCategory(String category, NetworkDelegate networkDelegate);

    void getMealsByCountry(String country, NetworkDelegate networkDelegate);

    //db
    void insertMealToFavourite(Meal meal);

    void deleteMealFromFavourite(Meal meal);

    void deleteAllFavouriteMeals();

    LiveData<List<Meal>> getAllFavouriteMeals();
}
