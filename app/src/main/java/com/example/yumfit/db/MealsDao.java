package com.example.yumfit.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.yumfit.pojo.Meal;

import java.util.List;

@Dao
public interface MealsDao {
    @Insert
    public void insertMeal(Meal meal);

    @Delete
    public void deleteMeal(Meal meal);

    @Query("DELETE FROM meals_table")
    public void deleteAllMeals() ;

    @Query("SELECT * FROM meals_table")
    LiveData<List<Meal>> getAllMeals();
}
