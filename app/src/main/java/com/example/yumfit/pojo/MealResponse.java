package com.example.yumfit.pojo;

import java.util.List;

public class MealResponse {

    private List<Meal> meals;

    public MealResponse(List<Meal> meals) {
        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
