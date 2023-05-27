package com.example.yumfit.details.view;

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
import com.example.yumfit.details.presenter.DetailsPresenter;
import com.example.yumfit.details.presenter.DetailsPresenterInterface;
import com.example.yumfit.network.ClientService;
import com.example.yumfit.network.RemoteSource;
import com.example.yumfit.pojo.Meal;
import com.example.yumfit.pojo.Repo;
import com.example.yumfit.pojo.RepoInterface;

import java.util.List;


public class DetailsFragment extends Fragment implements DetailsViewInterface {

    DetailsPresenterInterface detailsPresenter;
    //this id will pass as argument from fragment who navigate to details fragment don't forget
    String id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RemoteSource remoteSource = ClientService.getInstance(view.getContext());
        LocalSource localSource = ConcreteLocalSource.getInstance(view.getContext());
        RepoInterface repo = Repo.getInstance(remoteSource, localSource);
        detailsPresenter = new DetailsPresenter(repo, this);

        //this when i come to this fragment start to send request to fetch all data about this id
        detailsPresenter.getMealById(id);
    }

    @Override
    public void onGetMealDetails(List<Meal> meals) {
        //set data to your views
    }

    //this function will call from adapter or his click listener to start insert data to favourite
    @Override
    public void insertMealToFavourite(Meal meal) {
        detailsPresenter.insertMealToFavourite(meal);
    }

    @Override
    public void onFailToGetMealDetails(String message) {
        //tell user there is an error while fetch data
    }
}