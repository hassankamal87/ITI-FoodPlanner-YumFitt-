package com.example.yumfit.db;

import androidx.lifecycle.LiveData;

import com.example.yumfit.pojo.Meal;

import java.util.List;

public interface LocalSource {

    void insertMeal(Meal meal);

    void deleteMeal(Meal meal);

    void deleteAllMeals() ;

    LiveData<List<Meal>> getAllMeals();
}
