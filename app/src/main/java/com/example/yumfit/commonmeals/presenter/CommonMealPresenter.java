package com.example.yumfit.commonmeals.presenter;

import com.example.yumfit.commonmeals.view.CommonMealViewInterface;
import com.example.yumfit.network.NetworkDelegate;
import com.example.yumfit.pojo.Category;
import com.example.yumfit.pojo.Country;
import com.example.yumfit.pojo.Ingredient;
import com.example.yumfit.pojo.Meal;
import com.example.yumfit.pojo.MealResponse;
import com.example.yumfit.pojo.RepoInterface;

import java.util.List;

public class CommonMealPresenter implements NetworkDelegate, CommonMealPresenterInterface {
    private RepoInterface repo;
    private CommonMealViewInterface commonMealView;

    public CommonMealPresenter(RepoInterface repo, CommonMealViewInterface commonMealView) {
        this.repo = repo;
        this.commonMealView = commonMealView;
    }

    @Override
    public void insertMealToFavourite(Meal meal) {
        repo.insertMealToFavourite(meal);
    }

    @Override
    public void onSuccessResultMeal(List<Meal> meals) {

    }

    @Override
    public void onSuccessFilter(MealResponse meals) {

    }

    @Override
    public void onSuccessResultCategory(List<Category> categories) {

    }

    @Override
    public void onSuccessResultIngredient(List<Ingredient> ingredients) {

    }

    @Override
    public void onSuccessResultCountries(List<Country> countries) {

    }

    @Override
    public void onFailureResult(String message) {

    }
}
