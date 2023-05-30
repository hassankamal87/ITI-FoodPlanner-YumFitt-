package com.example.yumfit.profile.presenter;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.yumfit.pojo.Meal;
import com.example.yumfit.pojo.RepoInterface;
import com.example.yumfit.profile.view.ProfileViewInterface;

import java.util.List;

public class ProfilePresenter implements ProfilePresenterInterface{
    private RepoInterface repo;
    private ProfileViewInterface profileView;

    public ProfilePresenter(RepoInterface repo, ProfileViewInterface profileView){
        this.repo = repo;
        this.profileView = profileView;
    }

    @Override
    public void deleteAllFavouriteMeals() {
        repo.deleteAllFavouriteMeals();
    }

    @Override
    public void getAllFavouriteMeals() {
        repo.getAllFavouriteMeals().observe((LifecycleOwner) profileView, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                profileView.onGetAllFavouriteList(meals);
            }
        });
    }
}
