package com.example.yumfit.home.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.yumfit.R;
import com.example.yumfit.authentication.register.RegisterActivity;
import com.example.yumfit.db.ConcreteLocalSource;
import com.example.yumfit.db.LocalSource;
import com.example.yumfit.home.presenter.HomePresenter;
import com.example.yumfit.home.presenter.HomePresenterInterface;
import com.example.yumfit.network.ClientService;
import com.example.yumfit.network.RemoteSource;
import com.example.yumfit.pojo.Category;
import com.example.yumfit.pojo.Country;
import com.example.yumfit.pojo.Meal;
import com.example.yumfit.pojo.MealResponse;
import com.example.yumfit.pojo.Repo;
import com.example.yumfit.pojo.RepoInterface;
import com.example.yumfit.pojo.UserPojo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeViewFragment extends Fragment implements HomeViewInterface, OnClickInterface {

    RecyclerView dailyRecyclerView, countryRecyclerView, categoryRecyclerView;
    HomePresenterInterface presenter;
    DailyRecyclerAdapter dailyAdapter;
    CountryRecyclerAdapter countryAdapter;
    TextView dailyTV, countryTV, categoryTV;
    LottieAnimationView loadingLottie;
    CategoryRecyclerAdapter categoryAdapter;
    FirebaseFirestore db;
    boolean isExist = false;
    FirebaseUser currentUser;
    UserPojo userPojo;

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

        //get database From fireStore
        if (currentUser != null) {
            checkDataInFireStore();
        }else{
            presenter.deleteAllFavMeals();
        }
    }

    private void initializeViews(View view) {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        dailyRecyclerView = view.findViewById(R.id.dailyInspirationRecycler);
        countryRecyclerView = view.findViewById(R.id.countriesRecyclerView);
        categoryRecyclerView = view.findViewById(R.id.categoriesRecyclerView);
        dailyTV = view.findViewById(R.id.dailyTV);
        countryTV = view.findViewById(R.id.countriesTextView);
        categoryTV = view.findViewById(R.id.categoriesTextView);
        loadingLottie = view.findViewById(R.id.loadingLottie);
        dailyAdapter = new DailyRecyclerAdapter(view.getContext(), this);
        countryAdapter = new CountryRecyclerAdapter(view.getContext(), this);
        categoryAdapter = new CategoryRecyclerAdapter(view.getContext(), this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        dailyRecyclerView.setAdapter(dailyAdapter);

        countryRecyclerView.setAdapter(countryAdapter);

        categoryRecyclerView.setAdapter(categoryAdapter);

        db = FirebaseFirestore.getInstance();
        userPojo = new UserPojo();

    }

    @Override
    public void setDailyInspirationData(List<Meal> meals) {
        loadingLottie.setVisibility(View.GONE);
        dailyAdapter.setList((ArrayList<Meal>) meals);
        dailyAdapter.notifyDataSetChanged();
    }

    @Override
    public void setListToCategoriesAdapter(List<Category> categories) {
        loadingLottie.setVisibility(View.GONE);
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
        //Toast.makeText(dailyRecyclerView.getContext(), "error while : " + message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSaveBtnClick(Meal meal) {
        if (currentUser != null) {
            presenter.insertMeal(meal);
        } else {
            showMaterialDialog(getContext());
        }
    }

    @Override
    public void onDailyInspirationItemClicked(String id) {
        hideAnimation();
        HomeViewFragmentDirections.ActionHomeFragmentToDetailsFragment action =
                HomeViewFragmentDirections.actionHomeFragmentToDetailsFragment(id);
        Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void onCountryItemClicked(Country country) {
        presenter.getMealsByCountry(country.getStrArea());

    }

    @Override
    public void onSuccessToFilter(MealResponse meals) {
        hideAnimation();
        HomeViewFragmentDirections.ActionHomeFragmentToCommonMeals action =
                HomeViewFragmentDirections.actionHomeFragmentToCommonMeals(meals);
        Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void onCategoryItemClicked(Category category) {
        hideAnimation();
        presenter.getMealsByCategory(category.getStrCategory());
    }


    private void checkDataInFireStore2() {
        db.collection("users")
                .document(currentUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Map<String, Object> data = document.getData();
                            userPojo = document.toObject(UserPojo.class);
                            if (!document.exists()) {
                                createNewUserInFireStore();
                            }
                            if (userPojo.getFavMeals() != null)
                                presenter.insertAllFav(userPojo.getFavMeals());
                            isExist = true;
                        } else {
                            Log.d("hey", "Error getting documents.", task.getException());

                        }
                    }
                });

    }

    private void checkDataInFireStore() {
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getId().equals(currentUser.getUid())) {
                                    //her you need to contain data from FireStore to your object
                                    Map<String, Object> data = document.getData();
                                    userPojo = new UserPojo((Map<String, Object>) data.get("userPojo"));
                                    if (userPojo.getFavMeals() != null)
                                        presenter.insertAllFav(userPojo.getFavMeals());
                                    isExist = true;
                                }
                            }
                            if (!isExist) {
                                createNewUserInFireStore();
                            }
                        } else {
                            Log.d("hey", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void createNewUserInFireStore() {
        Map<String, Object> user = new HashMap<>();
        UserPojo newUser = new UserPojo(currentUser.getDisplayName(), currentUser.getEmail());
        user.put("userPojo", newUser);

        db.collection("users")
                .document(currentUser.getUid())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("hey", "new User Added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("hey", "Error adding document", e);
                    }
                });
    }

    private void showMaterialDialog(Context context) {

        new MaterialAlertDialogBuilder(context)
                .setTitle(getResources().getString(R.string.yumfit))
                .setMessage(getResources().getString(R.string.messageAdd))
                .setNegativeButton(getResources().getString(R.string.signIn), (dialog, which) -> {

                    Intent intent = new Intent();
                    intent.setClass(getContext(), RegisterActivity.class);
                    startActivity(intent);
                })
                .setPositiveButton(getResources().getString(R.string.cancel), (dialog, which) -> {

                    // Respond to negative button press
                })
        .show();
    }

    private void hideAnimation(){
        loadingLottie.setVisibility(View.GONE);
        dailyTV.setVisibility(View.VISIBLE);
        countryTV.setVisibility(View.VISIBLE);
        categoryTV.setVisibility(View.VISIBLE);
    }
}