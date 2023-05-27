package com.example.yumfit.network;

import android.content.Context;

import com.example.yumfit.pojo.CategoryResponse;
import com.example.yumfit.pojo.CountryResponse;
import com.example.yumfit.pojo.IngredientResponse;
import com.example.yumfit.pojo.MealResponse;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientService implements RemoteSource {
    private static ClientService instance = null;
    private MealApiInterface mealApiInterface;
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    private ClientService(Context context){
        File cacheDirectory = new File(context.getCacheDir(), "offline_cache_directory");
        Cache cache = new Cache(cacheDirectory,80 *1024 * 1024);

        OkHttpClient okHttpClient = new OkHttpClient
                .Builder().cache(cache).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mealApiInterface = retrofit.create(MealApiInterface.class);
    }

    public static synchronized ClientService getInstance(Context context){
        if(instance == null){
            instance = new ClientService(context);
        }
        return instance;
    }

    @Override
    public void getMealByName(String name, NetworkDelegate networkDelegate) {
        mealApiInterface.getMealByName(name).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                networkDelegate.onSuccessResultMeal(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkDelegate.onFailureResult(t.getMessage());
            }
        });
    }

    @Override
    public void getMealByFirstChar(String firstChar, NetworkDelegate networkDelegate) {
        mealApiInterface.getMealByFirstChar(firstChar).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                networkDelegate.onSuccessResultMeal(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkDelegate.onFailureResult(t.getMessage());
            }
        });
    }

    @Override
    public void getMealById(String id, NetworkDelegate networkDelegate) {
        mealApiInterface.getMealById(id).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                networkDelegate.onSuccessResultMeal(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkDelegate.onFailureResult(t.getMessage());
            }
        });
    }

    @Override
    public void getRandomMeal(NetworkDelegate networkDelegate) {
        mealApiInterface.getRandomMeal().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                networkDelegate.onSuccessResultMeal(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkDelegate.onFailureResult(t.getMessage());
            }
        });
    }

    @Override
    public void getAllCategories(NetworkDelegate networkDelegate) {
        mealApiInterface.getAllCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                networkDelegate.onSuccessResultCategory(response.body().getCategories());
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                networkDelegate.onFailureResult(t.getMessage());
            }
        });
    }

    @Override
    public void getAllCountries(NetworkDelegate networkDelegate) {
        mealApiInterface.getAllCountries().enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                networkDelegate.onSuccessResultCountries(response.body().getCountries());
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                networkDelegate.onFailureResult(t.getMessage());
            }
        });
    }

    @Override
    public void getAllIngredient(NetworkDelegate networkDelegate) {
        mealApiInterface.getAllIngredient().enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                networkDelegate.onSuccessResultIngredient(response.body().getIngredient());
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {
                networkDelegate.onFailureResult(t.getMessage());
            }
        });
    }

    @Override
    public void getMealsByIngredient(String ingredient, NetworkDelegate networkDelegate) {
        mealApiInterface.getMealsByIngredient(ingredient).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                networkDelegate.onSuccessResultMeal(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkDelegate.onFailureResult(t.getMessage());
            }
        });
    }

    @Override
    public void getMealsByCategory(String category, NetworkDelegate networkDelegate) {
        mealApiInterface.getMealsByCategory(category).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                networkDelegate.onSuccessResultMeal(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkDelegate.onFailureResult(t.getMessage());
            }
        });
    }

    @Override
    public void getMealsByCountry(String country, NetworkDelegate networkDelegate) {
        mealApiInterface.getMealsByCountry(country).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                networkDelegate.onSuccessResultMeal(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkDelegate.onFailureResult(t.getMessage());
            }
        });
    }
}
