package com.example.yumfit.db;

import androidx.lifecycle.LiveData;

import com.example.yumfit.pojo.Meal;

import java.util.List;

public interface LocalSource {

    void insertMeal(Meal meal);

    public void insertAllFav(List<Meal> meals);

    void deleteMeal(Meal meal);

    void deleteAllMeals() ;

    LiveData<List<Meal>> getAllMeals();
    LiveData<List<Meal>> getMealsOfDay(String day);
    void updateDayOfMeal(String id, String day);
}
