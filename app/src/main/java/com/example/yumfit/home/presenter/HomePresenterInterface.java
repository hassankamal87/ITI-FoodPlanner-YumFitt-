package com.example.yumfit.home.presenter;

import com.example.yumfit.network.NetworkDelegate;
import com.example.yumfit.pojo.Meal;

import java.util.List;

public interface HomePresenterInterface {
    void getRandomMeal();
    void getAllCategories();
    void getAllCountries();
    void getAllIngredient();
    void insertMeal(Meal meal);

    void insertAllFav(List<Meal> meals);
    void getMealsByCategory(String category);
    void getMealsByCountry(String country);

    void deleteAllFavMeals();
}
