package com.example.yumfit.search.presenter;

import com.example.yumfit.network.NetworkDelegate;
import com.example.yumfit.pojo.Meal;

public interface SearchPresenterInterface {
    void getMealsByIngredient(String ingredient);

    void getMealsByCategory(String category);

    void getMealsByCountry(String country);

    void getMealByName(String name);

    void getMealByFirstChar(String firstChar);
    void getRandomMeal();

    void insertMeal(Meal meal);
}
