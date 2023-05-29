package com.example.yumfit.daydetail.presenter;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.yumfit.daydetail.view.DayDetailFragment;
import com.example.yumfit.daydetail.view.DayViewInterface;
import com.example.yumfit.pojo.Meal;
import com.example.yumfit.pojo.RepoInterface;

import java.util.List;

public class DayDetailsPresenter implements DayPresenterInterface{
    private RepoInterface repo;
    private DayViewInterface dayViewInterface;
    public DayDetailsPresenter(RepoInterface repo, DayViewInterface dayViewInterface){
        this.repo = repo;
        this.dayViewInterface = dayViewInterface;
    }

    @Override
    public void getMealsForDay(String day) {
        repo.getMealsOfDay(day).observe((LifecycleOwner) dayViewInterface, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                dayViewInterface.onGetMealOfDay(meals);
            }
        });
    }

    @Override
    public void deleteMeal(Meal meal) {
        repo.deleteMealFromFavourite(meal);
    }

    @Override
    public void updateDayOfMeal(String id, String day) {
        repo.updateDayOfMeal(id, day);
    }


}
