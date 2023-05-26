package com.example.yumfit.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryResponse {
    @SerializedName("meals")
    private List<Country> countries;


    public CountryResponse(List<Country> meals) {
        this.countries = meals;
    }

    public List<Country> getMeals() {
        return countries;
    }

    public void setMeals(List<Country> meals) {
        this.countries = meals;
    }
}
