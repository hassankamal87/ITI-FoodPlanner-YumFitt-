package com.example.yumfit.network;

import com.example.yumfit.pojo.CategoryResponse;
import com.example.yumfit.pojo.CountryResponse;
import com.example.yumfit.pojo.IngredientResponse;
import com.example.yumfit.pojo.MealResponse;

import java.util.List;

import retrofit2.Call;

public interface RemoteSource {
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
}
