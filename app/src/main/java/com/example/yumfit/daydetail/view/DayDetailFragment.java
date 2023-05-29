package com.example.yumfit.daydetail.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.yumfit.R;
import com.example.yumfit.daydetail.presenter.DayDetailsPresenter;
import com.example.yumfit.daydetail.presenter.DayPresenterInterface;
import com.example.yumfit.db.ConcreteLocalSource;
import com.example.yumfit.db.LocalSource;
import com.example.yumfit.details.view.DetailsFragmentArgs;
import com.example.yumfit.network.ClientService;
import com.example.yumfit.network.RemoteSource;
import com.example.yumfit.pojo.Meal;
import com.example.yumfit.pojo.Repo;
import com.example.yumfit.pojo.RepoInterface;
import com.example.yumfit.ui.Home2Activity;

import java.util.ArrayList;
import java.util.List;


public class DayDetailFragment extends Fragment implements DayViewInterface, OnDayClickInterface {


    String day;
    RecyclerView mealsRecyclerPlan;
    DayPresenterInterface detailsPresenter;
    DayMealAdapter dayAdapter;

    @Override
    public void onStart() {
        super.onStart();
        ((Home2Activity) requireActivity()).bottomNavigationView.setVisibility(View.GONE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        day = DayDetailFragmentArgs.fromBundle(getArguments()).getDay();
        RemoteSource remoteSource = ClientService.getInstance(getContext());
        LocalSource localSource = ConcreteLocalSource.getInstance(getContext());
        RepoInterface repo = Repo.getInstance(remoteSource, localSource);
        detailsPresenter = new DayDetailsPresenter(repo, this);

        detailsPresenter.getMealsForDay(day);


    }

    void initializeViews(View view) {
        mealsRecyclerPlan = view.findViewById(R.id.mealsRecyclerPlan);
        dayAdapter = new DayMealAdapter(view.getContext(), this);
        mealsRecyclerPlan.setAdapter(dayAdapter);
    }

    @Override
    public void onGetMealOfDay(List<Meal> favouriteMeals) {
        if (favouriteMeals != null) {
            dayAdapter.setList((ArrayList<Meal>) favouriteMeals);
            dayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDeleteBtnClicked(Meal meal) {
        detailsPresenter.updateDayOfMeal(meal.getIdMeal(), "no day");
    }

    @Override
    public void onFavItemClicked(String id) {
        DayDetailFragmentDirections.ActionDayDetailFragmentToDetailsFragment action =
                DayDetailFragmentDirections.actionDayDetailFragmentToDetailsFragment(id);
        Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((Home2Activity) requireActivity()).bottomNavigationView.setVisibility(View.VISIBLE);
    }
}