package com.example.yumfit.favourite.presenter;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.yumfit.favourite.view.FavouriteViewInterface;
import com.example.yumfit.network.NetworkDelegate;
import com.example.yumfit.pojo.Category;
import com.example.yumfit.pojo.Country;
import com.example.yumfit.pojo.Ingredient;
import com.example.yumfit.pojo.Meal;
import com.example.yumfit.pojo.RepoInterface;

import java.util.List;

public class FavouritePresenter implements FavouritePresenterInterface {
    private RepoInterface repo;
    private FavouriteViewInterface favouriteView;


    public FavouritePresenter(RepoInterface repo, FavouriteViewInterface favouriteView){
        this.repo = repo;
        this.favouriteView = favouriteView;
    }

    @Override
    public void getAllMeals() {
        repo.getAllFavouriteMeals().observe((LifecycleOwner) favouriteView, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                favouriteView.onGetFavouriteMeals(meals);
            }
        });
    }

    @Override
    public void deleteMeal(Meal meal) {
        repo.deleteMealFromFavourite(meal);
    }
}
