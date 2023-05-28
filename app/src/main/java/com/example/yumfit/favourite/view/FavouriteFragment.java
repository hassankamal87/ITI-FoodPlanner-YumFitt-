package com.example.yumfit.favourite.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yumfit.R;
import com.example.yumfit.db.ConcreteLocalSource;
import com.example.yumfit.db.LocalSource;
import com.example.yumfit.favourite.presenter.FavouritePresenter;
import com.example.yumfit.favourite.presenter.FavouritePresenterInterface;
import com.example.yumfit.network.ClientService;
import com.example.yumfit.network.RemoteSource;
import com.example.yumfit.pojo.Meal;
import com.example.yumfit.pojo.Repo;
import com.example.yumfit.pojo.RepoInterface;

import java.util.ArrayList;
import java.util.List;


public class FavouriteFragment extends Fragment implements LifecycleOwner,FavouriteViewInterface, OnClickFavouriteInterface {

    FavouritePresenterInterface favouritePresenter;
    RecyclerView favouriteRecycler;
    FavouriteRecyclerAdapter favouriteAdapter;
    TextView nullText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nullText = view.findViewById(R.id.nullTextView);
        RemoteSource remoteSource = ClientService.getInstance(view.getContext());
        LocalSource localSource = ConcreteLocalSource.getInstance(view.getContext());
        RepoInterface repo = Repo.getInstance(remoteSource, localSource);
        favouritePresenter = new FavouritePresenter(repo, this);

        favouriteRecycler = view.findViewById(R.id.favouriteRecyclerView);
        favouriteAdapter = new FavouriteRecyclerAdapter(view.getContext(), this);
        favouriteRecycler.setAdapter(favouriteAdapter);
        favouritePresenter.getAllMeals();

    }

    @Override
    public void onGetFavouriteMeals(List<Meal> favouriteMeals) {
        if (favouriteMeals.isEmpty()){
            nullText.setVisibility(View.VISIBLE);
        }else{
            nullText.setVisibility(View.GONE);
        }
        favouriteAdapter.setList((ArrayList<Meal>) favouriteMeals);
        favouriteAdapter.notifyDataSetChanged();
    }
    @Override
    public void deleteMealFromFavourite(Meal meal) {
        favouritePresenter.deleteMeal(meal);
    }


    @Override
    public void onDeleteBtnClicked(Meal meal) {
        favouritePresenter.deleteMeal(meal);
    }

    @Override
    public void onFavItemClicked(String id) {
        FavouriteFragmentDirections.ActionFavouriteFragmentToDetailsFragment action =
                FavouriteFragmentDirections.actionFavouriteFragmentToDetailsFragment(id);
        Navigation.findNavController(getView()).navigate(action);
    }
}