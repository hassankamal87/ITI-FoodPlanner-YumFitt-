package com.example.yumfit.daydetail.presenter;

import androidx.lifecycle.LiveData;

import com.example.yumfit.pojo.Meal;

import java.util.List;

public interface DayPresenterInterface {
    void getMealsForDay(String day);
    void deleteMeal(Meal meal);

    void updateDayOfMeal(String id, String day);
}
