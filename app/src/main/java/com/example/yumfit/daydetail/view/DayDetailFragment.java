package com.example.yumfit.daydetail.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
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
import android.widget.TextView;
import android.widget.Toast;

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

    TextView nameDay;
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
        nameDay.setText(day);

    }

    void initializeViews(View view) {
        nameDay = view.findViewById(R.id.dayName);
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
        if (checkConnection()) {
            DayDetailFragmentDirections.ActionDayDetailFragmentToDetailsFragment action =
                    DayDetailFragmentDirections.actionDayDetailFragmentToDetailsFragment(id);
            Navigation.findNavController(getView()).navigate(action);
        }else{
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((Home2Activity) requireActivity()).bottomNavigationView.setVisibility(View.VISIBLE);
    }

    private boolean checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        boolean isConnected = networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        return isConnected;
    }
}