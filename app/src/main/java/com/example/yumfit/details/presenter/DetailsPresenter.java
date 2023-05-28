package com.example.yumfit.details.presenter;

import com.example.yumfit.details.view.DetailsViewInterface;
import com.example.yumfit.network.NetworkDelegate;
import com.example.yumfit.pojo.Category;
import com.example.yumfit.pojo.Country;
import com.example.yumfit.pojo.Ingredient;
import com.example.yumfit.pojo.Meal;
import com.example.yumfit.pojo.RepoInterface;

import java.util.List;

public class DetailsPresenter implements NetworkDelegate, DetailsPresenterInterface{
    private RepoInterface repo;
    private DetailsViewInterface detailsView;

    public DetailsPresenter(RepoInterface repo, DetailsViewInterface detailsView){
        this.repo = repo;
        this.detailsView = detailsView;
    }

    @Override
    public void getMealById(String id) {
        repo.getMealById(id,this);

    }

    @Override
    public void insertMealToFavourite(Meal meal) {
        repo.insertMealToFavourite(meal);
    }

    @Override
    public void onSuccessResultMeal(List<Meal> meals) {
        detailsView.onGetMealDetails(meals);
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
        detailsView.onFailToGetMealDetails(message);
    }
}
