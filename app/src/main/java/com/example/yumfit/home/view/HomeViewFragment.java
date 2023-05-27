package com.example.yumfit.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yumfit.R;
import com.example.yumfit.db.ConcreteLocalSource;
import com.example.yumfit.db.LocalSource;
import com.example.yumfit.home.presenter.HomePresenter;
import com.example.yumfit.home.presenter.HomePresenterInterface;
import com.example.yumfit.network.ClientService;
import com.example.yumfit.network.RemoteSource;
import com.example.yumfit.pojo.Category;
import com.example.yumfit.pojo.Country;
import com.example.yumfit.pojo.Ingredient;
import com.example.yumfit.pojo.Meal;
import com.example.yumfit.pojo.Repo;
import com.example.yumfit.pojo.RepoInterface;

import java.util.List;


public class HomeViewFragment extends Fragment implements HomeViewInterface {


    ImageView dailyMealImageView;
    TextView dailyMealNameTextView;
    TextView dailyMealCountryTextView;
    ImageView dailyMealAddToFavouriteBtn;
    RecyclerView categoriesRecyclerView, countriesRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);

        RemoteSource remoteSource = ClientService.getInstance(view.getContext());
        LocalSource localSource = ConcreteLocalSource.getInstance(view.getContext());
        RepoInterface repo = Repo.getInstance(remoteSource, localSource);
        HomePresenterInterface presenter = new HomePresenter(repo, this);

    }

    private void initializeViews(View view) {
        dailyMealImageView = view.findViewById(R.id.mealImg);
        dailyMealNameTextView = view.findViewById(R.id.mealName);
        dailyMealCountryTextView = view.findViewById(R.id.countryTextView);
        dailyMealAddToFavouriteBtn = view.findViewById(R.id.addToFavouriteBtn);
        categoriesRecyclerView = view.findViewById(R.id.categoriesRecyclerView);
        countriesRecyclerView = view.findViewById(R.id.countriesRecyclerView);
    }

    @Override
    public void setDailyInspirationData(List<Meal> meals) {
        //what happen when get data from remote
    }

    @Override
    public void addMealToFavouriteList(Meal meal) {

    }

    @Override
    public void setListToCategoriesAdapter(List<Category> categories) {

    }

    @Override
    public void setListToIngredientAdapter(List<Ingredient> ingredients) {

    }

    @Override
    public void setListToCountriesAdapter(List<Country> countries) {

    }

    @Override
    public void onFailureResult(String message) {

    }
}