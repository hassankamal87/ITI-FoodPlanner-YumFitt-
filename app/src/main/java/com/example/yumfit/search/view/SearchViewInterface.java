package com.example.yumfit.search.view;

import com.example.yumfit.pojo.Category;
import com.example.yumfit.pojo.Country;
import com.example.yumfit.pojo.Ingredient;
import com.example.yumfit.pojo.Meal;

import java.util.List;

public interface SearchViewInterface{

    void onGetMeals(List<Meal> meals);
    void onGetAllCategories(List<Category> categories);

    void onGetAllIngredient(List<Ingredient> ingredients);

    void onGetAllCountries(List<Country> countries);

    void onFailureResult(String message);
}
