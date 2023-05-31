package com.example.yumfit.favourite.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yumfit.R;
import com.example.yumfit.authentication.register.RegisterActivity;
import com.example.yumfit.db.ConcreteLocalSource;
import com.example.yumfit.db.LocalSource;
import com.example.yumfit.favourite.presenter.FavouritePresenter;
import com.example.yumfit.favourite.presenter.FavouritePresenterInterface;
import com.example.yumfit.home.view.HomeViewFragment;
import com.example.yumfit.network.ClientService;
import com.example.yumfit.network.RemoteSource;
import com.example.yumfit.pojo.Meal;
import com.example.yumfit.pojo.Repo;
import com.example.yumfit.pojo.RepoInterface;
import com.example.yumfit.pojo.UserPojo;
import com.example.yumfit.ui.Home2Activity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FavouriteFragment extends Fragment implements LifecycleOwner, FavouriteViewInterface, OnClickFavouriteInterface {

    FavouritePresenterInterface favouritePresenter;
    RecyclerView favouriteRecycler;
    FavouriteRecyclerAdapter favouriteAdapter;
    TextView nullText;

    Button refreshBtn;

    FirebaseUser currentUser;

    List<Meal> mealsFromRoom;

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
        refreshBtn = view.findViewById(R.id.refresh);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        RemoteSource remoteSource = ClientService.getInstance(view.getContext());
        LocalSource localSource = ConcreteLocalSource.getInstance(view.getContext());
        RepoInterface repo = Repo.getInstance(remoteSource, localSource);
        favouritePresenter = new FavouritePresenter(repo, this);

        favouriteRecycler = view.findViewById(R.id.favouriteRecyclerView);
        favouriteAdapter = new FavouriteRecyclerAdapter(view.getContext(), this);
        favouriteRecycler.setAdapter(favouriteAdapter);
        favouritePresenter.getAllMeals();

        if (currentUser == null) {
            showMaterialDialog(view.getContext());
            nullText.setText("Sign In and Comeback..");
        }

        if (!checkConnection()) {
            refreshBtn.setVisibility(View.VISIBLE);
        }

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkConnection()) {
                    connectFragments();
                }
            }
        });

    }

    @Override
    public void onGetFavouriteMeals(List<Meal> favouriteMeals) {
        if (favouriteMeals.isEmpty()) {
            nullText.setVisibility(View.VISIBLE);
        } else {
            nullText.setVisibility(View.GONE);
        }
        favouriteAdapter.setList((ArrayList<Meal>) favouriteMeals);
        favouriteAdapter.notifyDataSetChanged();
        mealsFromRoom = favouriteMeals;
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
        if (checkConnection()) {
            FavouriteFragmentDirections.ActionFavouriteFragmentToDetailsFragment action =
                    FavouriteFragmentDirections.actionFavouriteFragmentToDetailsFragment(id);
            Navigation.findNavController(getView()).navigate(action);
        }else{
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUserDataInFireStore() {
        UserPojo updatedUser = new UserPojo(currentUser.getDisplayName(), currentUser.getEmail(),
                mealsFromRoom);
        Map<String, Object> data = new HashMap<>();
        data.put("userPojo", updatedUser);
        FirebaseFirestore.getInstance().collection("users")
                .document(currentUser.getUid())
                .set(data, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("hey", "User updated successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("hey", "Error updating user", e);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (currentUser != null) {
            updateUserDataInFireStore();
        }
    }

    private void showMaterialDialog(Context context) {

        new MaterialAlertDialogBuilder(context)
                .setTitle(getResources().getString(R.string.yumfit))
                .setMessage(getResources().getString(R.string.messageFav))
                .setNegativeButton(getResources().getString(R.string.signIn), (dialog, which) -> {

                    Intent intent = new Intent();
                    intent.setClass(getContext(), RegisterActivity.class);
                    startActivity(intent);
                })
                .setPositiveButton(getResources().getString(R.string.cancel), (dialog, which) -> {


                })
                .show();
    }

    private boolean checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        boolean isConnected = networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        return isConnected;
    }

    private void connectFragments() {

        Home2Activity activity = (Home2Activity) getActivity();
        HomeViewFragment fragment = new HomeViewFragment();
        fragment.setArguments(new Bundle());
        activity.navController.navigate(R.id.homeFragment);
        Menu menu = activity.bottomNavigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.homeFragment);
        MenuItem menuItem2 = menu.findItem(R.id.searchFragment);
        MenuItem menuItem3 = menu.findItem(R.id.profileFragment);
        menuItem.setEnabled(true);
        menuItem2.setEnabled(true);
        menuItem3.setEnabled(true);
    }
}