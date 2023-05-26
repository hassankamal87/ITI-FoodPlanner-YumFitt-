package com.example.yumfit.pojo;

import java.util.List;

public class MealResponse {
    private List<Meal> Meals;

    public MealResponse(List<Meal> meals) {
        Meals = meals;
    }

    public List<Meal> getMeals() {
        return Meals;
    }

    public void setMeals(List<Meal> meals) {
        Meals = meals;
    }
}
