package com.example.yumfit.network;

import android.content.Context;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientService {
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
}
