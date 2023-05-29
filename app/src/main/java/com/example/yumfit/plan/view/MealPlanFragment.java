package com.example.yumfit.plan.view;

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


public class MealPlanFragment extends Fragment implements OnPlanClickInterface {

    RecyclerView planRecyclerView;
    PlanRecyclerAdapter planAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        planRecyclerView = view.findViewById(R.id.planRecyclerView);
        planAdapter = new PlanRecyclerAdapter(this);
        planRecyclerView.setAdapter(planAdapter);
        planAdapter.setList();
    }

    @Override
    public void onShowBtnClicked(String day) {
        //we should navigate to fragment show plan for these day
        MealPlanFragmentDirections.ActionMealPlanFragmentToDayDetailFragment action =
                MealPlanFragmentDirections.actionMealPlanFragmentToDayDetailFragment(day);
        Navigation.findNavController(getView()).navigate(action);
    }
}