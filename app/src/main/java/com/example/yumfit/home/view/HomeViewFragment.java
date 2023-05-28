package com.example.yumfit.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;


public class HomeViewFragment extends Fragment implements HomeViewInterface, OnClickInterface {

    RecyclerView dailyRecyclerView, countryRecyclerView, categoryRecyclerView;
    HomePresenterInterface presenter;
    DailyRecyclerAdapter dailyAdapter;
    CountryRecyclerAdapter countryAdapter;
    CategoryRecyclerAdapter categoryAdapter;
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
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        RemoteSource remoteSource = ClientService.getInstance(view.getContext());
        LocalSource localSource = ConcreteLocalSource.getInstance(view.getContext());
        RepoInterface repo = Repo.getInstance(remoteSource, localSource);
        presenter = new HomePresenter(repo, this);

        presenter.getRandomMeal();
        presenter.getAllCountries();
        presenter.getAllCategories();

    }

    private void initializeViews(View view) {
        dailyRecyclerView = view.findViewById(R.id.dailyInspirationRecycler);
        countryRecyclerView = view.findViewById(R.id.countriesRecyclerView);
        categoryRecyclerView = view.findViewById(R.id.categoriesRecyclerView);
        dailyAdapter = new DailyRecyclerAdapter(view.getContext(),this);
        countryAdapter = new CountryRecyclerAdapter(view.getContext(), this);
        categoryAdapter = new CategoryRecyclerAdapter(view.getContext(), this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        dailyRecyclerView.setAdapter(dailyAdapter);
        //dailyRecyclerView.setLayoutManager(linearLayoutManager);

        countryRecyclerView.setAdapter(countryAdapter);
        //countryRecyclerView.setLayoutManager(linearLayoutManager);

        categoryRecyclerView.setAdapter(categoryAdapter);
        //categoryRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void setDailyInspirationData(List<Meal> meals) {
        dailyAdapter.setList((ArrayList<Meal>) meals);
        dailyAdapter.notifyDataSetChanged();
    }

    @Override
    public void setListToCategoriesAdapter(List<Category> categories) {
        categoryAdapter.setList((ArrayList<Category>) categories);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void setListToCountriesAdapter(List<Country> countries) {
        countryAdapter.setList((ArrayList<Country>) countries);
        countryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailureResult(String message) {
        Toast.makeText(dailyRecyclerView.getContext(), "error while : "+ message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveBtnClick(Meal meal) {
        presenter.insertMeal(meal);
    }

    @Override
    public void onDailyInspirationItemClicked(Meal meal) {
        //here we need to navigate to details screen

        Toast.makeText(dailyRecyclerView.getContext(), meal.getStrMeal()+ " clicked", Toast.LENGTH_SHORT).show();
        HomeViewFragmentDirections.ActionHomeFragmentToDetailsFragment action =
                HomeViewFragmentDirections.actionHomeFragmentToDetailsFragment(meal.getIdMeal());
        Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void onCountryItemClicked(Country country) {
        //here we need to navigate to country details screen
        Toast.makeText(dailyRecyclerView.getContext(), country.getStrArea()+ " clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryItemClicked(Category category) {
        //here we need to navigate to country details screen
        Toast.makeText(dailyRecyclerView.getContext(), category.getStrCategory()+ " clicked", Toast.LENGTH_SHORT).show();

    }

}