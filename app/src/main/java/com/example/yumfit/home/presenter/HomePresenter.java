package com.example.yumfit.home.presenter;

import com.example.yumfit.home.view.HomeViewInterface;
import com.example.yumfit.network.NetworkDelegate;
import com.example.yumfit.pojo.Category;
import com.example.yumfit.pojo.Country;
import com.example.yumfit.pojo.Ingredient;
import com.example.yumfit.pojo.Meal;
import com.example.yumfit.pojo.RepoInterface;

import java.util.List;

public class HomePresenter implements NetworkDelegate, HomePresenterInterface {
    private RepoInterface repo;
    private HomeViewInterface homeView;

    public HomePresenter(RepoInterface repo, HomeViewInterface home){
        this.repo = repo;
        this.homeView = home;
    }

    //presenter
    @Override
    public void getRandomMeal() {
        repo.getRandomMeal(this);
    }

    @Override
    public void getAllCategories() {
        repo.getAllCategories(this);
    }

    @Override
    public void getAllCountries() {
        repo.getAllCountries(this);
    }

    @Override
    public void getAllIngredient() {
        repo.getAllIngredient(this);
    }

    @Override
    public void insertMeal(Meal meal) {
        repo.insertMealToFavourite(meal);
    }

    //Delegate
    @Override
    public void onSuccessResultMeal(List<Meal> meals) {
        homeView.setDailyInspirationData(meals);
    }

    @Override
    public void onSuccessResultCategory(List<Category> categories) {
        homeView.setListToCategoriesAdapter(categories);
    }

    @Override
    public void onSuccessResultIngredient(List<Ingredient> ingredients) {
        homeView.setListToIngredientAdapter(ingredients);
    }

    @Override
    public void onSuccessResultCountries(List<Country> countries) {
        homeView.setListToCountriesAdapter(countries);
    }

    @Override
    public void onFailureResult(String message) {
        homeView.onFailureResult(message);
    }
}
