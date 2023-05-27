package com.example.yumfit.network;

import com.example.yumfit.pojo.Category;
import com.example.yumfit.pojo.CategoryResponse;
import com.example.yumfit.pojo.Country;
import com.example.yumfit.pojo.CountryResponse;
import com.example.yumfit.pojo.Ingredient;
import com.example.yumfit.pojo.IngredientResponse;
import com.example.yumfit.pojo.Meal;
import com.example.yumfit.pojo.MealResponse;

import java.util.List;

public interface NetworkDelegate {
    public void onSuccessResultMeal(List<Meal> meals);
    public void onSuccessResultCategory(List<Category> categories);
    public void onSuccessResultIngredient(List<Ingredient> ingredients);
    public void onSuccessResultCountries(List<Country> countries);
    public void onFailureResult(String message);
}
