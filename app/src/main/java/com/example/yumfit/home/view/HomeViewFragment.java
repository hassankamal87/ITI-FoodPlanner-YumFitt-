package com.example.yumfit.home.view;

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
import com.example.yumfit.pojo.MealResponse;
import com.example.yumfit.pojo.Repo;
import com.example.yumfit.pojo.RepoInterface;
import com.example.yumfit.pojo.UserPojo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
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

        checkDataInFireStore();

        RemoteSource remoteSource = ClientService.getInstance(view.getContext());
        LocalSource localSource = ConcreteLocalSource.getInstance(view.getContext());
        RepoInterface repo = Repo.getInstance(remoteSource, localSource);
        presenter = new HomePresenter(repo, this);

        presenter.getRandomMeal();
        presenter.getAllCountries();
        presenter.getAllCategories();

    }

    private void initializeViews(View view) {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        dailyRecyclerView = view.findViewById(R.id.dailyInspirationRecycler);
        countryRecyclerView = view.findViewById(R.id.countriesRecyclerView);
        categoryRecyclerView = view.findViewById(R.id.categoriesRecyclerView);
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
        Toast.makeText(dailyRecyclerView.getContext(), "error while : " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveBtnClick(Meal meal) {
        presenter.insertMeal(meal);
    }

    @Override
    public void onDailyInspirationItemClicked(Meal meal) {
        HomeViewFragmentDirections.ActionHomeFragmentToDetailsFragment action =
                HomeViewFragmentDirections.actionHomeFragmentToDetailsFragment(meal.getIdMeal());
        Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void onCountryItemClicked(Country country) {
        presenter.getMealsByCountry(country.getStrArea());

    }

    @Override
    public void onSuccessToFilter(MealResponse meals) {
        HomeViewFragmentDirections.ActionHomeFragmentToCommonMeals action =
                HomeViewFragmentDirections.actionHomeFragmentToCommonMeals(meals);
        Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void onCategoryItemClicked(Category category) {
        presenter.getMealsByCategory(category.getStrCategory());
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

}