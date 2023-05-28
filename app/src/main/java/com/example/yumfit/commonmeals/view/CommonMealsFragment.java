package com.example.yumfit.commonmeals.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yumfit.R;
import com.example.yumfit.commonmeals.presenter.CommonMealPresenter;
import com.example.yumfit.commonmeals.presenter.CommonMealPresenterInterface;
import com.example.yumfit.db.ConcreteLocalSource;
import com.example.yumfit.db.LocalSource;
import com.example.yumfit.network.ClientService;
import com.example.yumfit.network.RemoteSource;
import com.example.yumfit.pojo.Meal;
import com.example.yumfit.pojo.MealResponse;
import com.example.yumfit.pojo.Repo;
import com.example.yumfit.pojo.RepoInterface;
import com.example.yumfit.ui.Home2Activity;

import java.util.ArrayList;


public class CommonMealsFragment extends Fragment implements OnCommonClickInterface, CommonMealViewInterface {

    MealResponse meals;
    RecyclerView mealRecycler;
    CommonMealAdapter commonMealAdapter;
    CommonMealPresenterInterface presenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        ((Home2Activity) requireActivity()).bottomNavigationView.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_common_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealRecycler = view.findViewById(R.id.mealRecycler);
        commonMealAdapter = new CommonMealAdapter(view.getContext(), this);
        meals = CommonMealsFragmentArgs.fromBundle(getArguments()).getMeals();
        mealRecycler.setAdapter(commonMealAdapter);

        commonMealAdapter.setList((ArrayList<Meal>) meals.getMeals());
        commonMealAdapter.notifyDataSetChanged();

        RemoteSource remoteSource = ClientService.getInstance(view.getContext());
        LocalSource localSource = ConcreteLocalSource.getInstance(view.getContext());
        RepoInterface repo = Repo.getInstance(remoteSource, localSource);
        presenter = new CommonMealPresenter(repo, this);
    }

    @Override
    public void onResume() {
        super.onResume();

        ((Home2Activity) requireActivity()).bottomNavigationView.setVisibility(View.GONE);
    }

    @Override
    public void onSaveBtnClicked(Meal meal) {
        presenter.insertMealToFavourite(meal);
    }

    @Override
    public void onMealItemClicked(String id) {
        CommonMealsFragmentDirections.ActionCommonMealsToDetailsFragment action =
                CommonMealsFragmentDirections.actionCommonMealsToDetailsFragment(id);
        Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ((Home2Activity) requireActivity()).bottomNavigationView.setVisibility(View.VISIBLE);
    }
}