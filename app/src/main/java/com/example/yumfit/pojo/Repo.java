package com.example.yumfit.pojo;

import androidx.lifecycle.LiveData;

import com.example.yumfit.db.LocalSource;
import com.example.yumfit.network.NetworkDelegate;
import com.example.yumfit.network.RemoteSource;

import java.util.List;

public class Repo implements RepoInterface {
    private static Repo instance;
    private RemoteSource remoteSource;
    private LocalSource localSource;

    private Repo(RemoteSource remoteSource, LocalSource localSource){
        this.remoteSource = remoteSource;
        this.localSource = localSource;
    }

    public static synchronized Repo getInstance(RemoteSource remoteSource, LocalSource localSource){
        if(instance == null){
            instance = new Repo(remoteSource, localSource);
        }
        return instance;
    }


    @Override
    public void getMealByName(String name, NetworkDelegate networkDelegate) {
        remoteSource.getMealByName(name, networkDelegate);
    }

    @Override
    public void getMealByFirstChar(String firstChar, NetworkDelegate networkDelegate) {
        remoteSource.getMealByFirstChar(firstChar, networkDelegate);
    }

    @Override
    public void getMealById(String id, NetworkDelegate networkDelegate) {
        remoteSource.getMealById(id, networkDelegate);
    }

    @Override
    public void getRandomMeal(NetworkDelegate networkDelegate) {
        remoteSource.getRandomMeal(networkDelegate);
    }

    @Override
    public void getAllCategories(NetworkDelegate networkDelegate) {
        remoteSource.getAllCategories(networkDelegate);
    }

    @Override
    public void getAllCountries(NetworkDelegate networkDelegate) {
        remoteSource.getAllCountries(networkDelegate);
    }

    @Override
    public void getAllIngredient(NetworkDelegate networkDelegate) {
        remoteSource.getAllIngredient(networkDelegate);
    }

    @Override
    public void getMealsByIngredient(String ingredient, NetworkDelegate networkDelegate) {
        remoteSource.getMealsByIngredient(ingredient, networkDelegate);
    }

    @Override
    public void getMealsByCategory(String category, NetworkDelegate networkDelegate) {
        remoteSource.getMealsByCategory(category, networkDelegate);
    }

    @Override
    public void getMealsByCountry(String country, NetworkDelegate networkDelegate) {
        remoteSource.getMealsByCountry(country, networkDelegate);
    }

    @Override
    public void insertMealToFavourite(Meal meal) {
        localSource.insertMeal(meal);
    }

    @Override
    public void deleteMealFromFavourite(Meal meal) {
        localSource.deleteMeal(meal);
    }

    @Override
    public void deleteAllFavouriteMeals() {
        localSource.deleteAllMeals();
    }

    @Override
    public LiveData<List<Meal>> getAllFavouriteMeals() {
        return localSource.getAllMeals();
    }
}
