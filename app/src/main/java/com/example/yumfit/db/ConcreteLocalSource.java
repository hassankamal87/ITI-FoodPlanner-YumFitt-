package com.example.yumfit.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.yumfit.pojo.Meal;

import java.util.List;

public class ConcreteLocalSource implements LocalSource {
    private static ConcreteLocalSource instance = null;
    private MealsDao mealsDao;

    private ConcreteLocalSource(Context context){
        mealsDao = MealsDatabase.getInstance(context).getMealsDao();
    }

    public static synchronized ConcreteLocalSource getInstance(Context context){
        if(instance == null){
            instance = new ConcreteLocalSource(context);
        }
        return instance;
    }


    @Override
    public void insertMeal(Meal meal) {
        mealsDao.insertMeal(meal);
    }

    @Override
    public void deleteMeal(Meal meal) {
        mealsDao.deleteMeal(meal);
    }

    @Override
    public void deleteAllMeals() {
        mealsDao.deleteAllMeals();
    }

    @Override
    public LiveData<List<Meal>> getAllMeals() {
        return mealsDao.getAllMeals();
    }

    @Override
    public LiveData<List<Meal>> getMealsOfDay(String day) {
        return mealsDao.getMealsOfDay(day);
    }

    @Override
    public void updateDayOfMeal(String id, String day) {
        mealsDao.updateColumnDay(id,day);
    }
}
