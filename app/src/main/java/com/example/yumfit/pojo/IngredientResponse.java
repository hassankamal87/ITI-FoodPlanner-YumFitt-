package com.example.yumfit.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientResponse {
    @SerializedName("meals")
    private List<Ingredient> Ingredients;

    public IngredientResponse(List<Ingredient> Ingredients) {
        this.Ingredients = Ingredients;
    }

    public List<Ingredient> getMeals() {
        return Ingredients;
    }

    public void setMeals(List<Ingredient> meals) {
        this.Ingredients = meals;
    }
}
