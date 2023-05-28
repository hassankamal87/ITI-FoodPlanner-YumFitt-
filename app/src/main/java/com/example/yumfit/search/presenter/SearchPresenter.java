package com.example.yumfit.search.presenter;

import com.example.yumfit.network.NetworkDelegate;
import com.example.yumfit.pojo.Category;
import com.example.yumfit.pojo.Country;
import com.example.yumfit.pojo.Ingredient;
import com.example.yumfit.pojo.Meal;
import com.example.yumfit.pojo.MealResponse;
import com.example.yumfit.pojo.RepoInterface;
import com.example.yumfit.search.view.SearchViewInterface;

import java.util.List;

public class SearchPresenter implements NetworkDelegate, SearchPresenterInterface{
    private RepoInterface repo;
    private SearchViewInterface searchView;

    public SearchPresenter(RepoInterface repo, SearchViewInterface searchView){
        this.repo = repo;
        this.searchView = searchView;
    }

    @Override
    public void getMealsByIngredient(String ingredient) {
        repo.getMealsByIngredient(ingredient, this);
    }

    @Override
    public void getMealsByCategory(String category) {
        repo.getMealsByCategory(category, this);
    }

    @Override
    public void getMealsByCountry(String country) {
        repo.getMealsByCountry(country, this);
    }

    @Override
    public void getMealByName(String name) {
        repo.getMealByName(name, this);
    }

    @Override
    public void getMealByFirstChar(String firstChar) {
        repo.getMealByFirstChar(firstChar, this);
    }

    @Override
    public void getMealById(String id) {
        repo.getMealById(id, this);
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

    @Override
    public void onSuccessResultMeal(List<Meal> meals) {
        searchView.onGetMeals(meals);
    }

    @Override
    public void onSuccessFilter(MealResponse meals) {

    }

    @Override
    public void onSuccessResultCategory(List<Category> categories) {
        searchView.onGetAllCategories(categories);
    }

    @Override
    public void onSuccessResultIngredient(List<Ingredient> ingredients) {
        searchView.onGetAllIngredient(ingredients);
    }

    @Override
    public void onSuccessResultCountries(List<Country> countries) {
        searchView.onGetAllCountries(countries);
    }

    @Override
    public void onFailureResult(String message) {
        searchView.onFailureResult(message);
    }
}
