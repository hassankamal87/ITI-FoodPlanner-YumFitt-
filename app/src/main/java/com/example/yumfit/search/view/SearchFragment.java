package com.example.yumfit.search.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yumfit.R;
import com.example.yumfit.db.ConcreteLocalSource;
import com.example.yumfit.db.LocalSource;
import com.example.yumfit.network.ClientService;
import com.example.yumfit.network.RemoteSource;
import com.example.yumfit.pojo.Category;
import com.example.yumfit.pojo.Country;
import com.example.yumfit.pojo.Ingredient;
import com.example.yumfit.pojo.Meal;
import com.example.yumfit.pojo.Repo;
import com.example.yumfit.pojo.RepoInterface;
import com.example.yumfit.search.presenter.SearchPresenter;
import com.example.yumfit.search.presenter.SearchPresenterInterface;

import java.util.List;


public class SearchFragment extends Fragment implements SearchViewInterface {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RemoteSource remoteSource = ClientService.getInstance(view.getContext());
        LocalSource localSource = ConcreteLocalSource.getInstance(view.getContext());
        RepoInterface repo = Repo.getInstance(remoteSource, localSource);
        SearchPresenterInterface searchPresenter = new SearchPresenter(repo, this);


    }

    @Override
    public void onGetMeals(List<Meal> meals) {

    }

    @Override
    public void onGetAllCategories(List<Category> categories) {

    }

    @Override
    public void onGetAllIngredient(List<Ingredient> ingredients) {

    }

    @Override
    public void onGetAllCountries(List<Country> countries) {

    }

    @Override
    public void onFailureResult(String message) {

    }
}