package com.example.yumfit.home.presenter;

import com.example.yumfit.pojo.Meal;

public interface HomePresenterInterface {
    void getRandomMeal();
    void getAllCategories();
    void getAllCountries();
    void getAllIngredient();
    void insertMeal(Meal meal);
}
