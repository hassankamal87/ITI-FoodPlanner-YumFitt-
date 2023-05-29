package com.example.yumfit.search.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.yumfit.R;
import com.example.yumfit.db.ConcreteLocalSource;
import com.example.yumfit.db.LocalSource;
import com.example.yumfit.network.ClientService;
import com.example.yumfit.network.RemoteSource;
import com.example.yumfit.pojo.Category;
import com.example.yumfit.pojo.Country;
import com.example.yumfit.pojo.Ingredient;
import com.example.yumfit.pojo.Meal;
import com.example.yumfit.pojo.Repo;
import com.example.yumfit.pojo.RepoInterface;
import com.example.yumfit.search.presenter.SearchPresenter;
import com.example.yumfit.search.presenter.SearchPresenterInterface;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements SearchViewInterface, OnSearchClickInterface {

    EditText searchET;
    RecyclerView searchRecycler;
    SearchPresenterInterface searchPresenter;
    SearchRecyclerAdapter searchRecyclerAdapter;

    RadioGroup radioGroup;
    TextView nullTextView;
    int selectedBtnId;

    ArrayList<Meal> emptyMeals = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        RemoteSource remoteSource = ClientService.getInstance(view.getContext());
        LocalSource localSource = ConcreteLocalSource.getInstance(view.getContext());
        RepoInterface repo = Repo.getInstance(remoteSource, localSource);
        searchPresenter = new SearchPresenter(repo, this);

        searchPresenter.getMealsByCountry("egyptian");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.meal_radioBtn:
                        searchPresenter.getRandomMeal();
                        break;
                    case R.id.country_radioBtn:
                        searchPresenter.getMealsByCountry("egyptian");
                        break;
                    case R.id.category_radioBtn:
                        searchPresenter.getMealsByCategory("seafood");
                        break;
                    case R.id.ingredient_radioBtn:
                        searchPresenter.getMealsByIngredient("Coconut Milk");
                        break;
                    default:
                        break;
                }
                selectedBtnId = checkedId;

            }
        });
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString();
                switch (selectedBtnId) {
                    case R.id.meal_radioBtn:
                        searchPresenter.getMealByName(query);
                        break;
                    case R.id.country_radioBtn:
                        searchPresenter.getMealsByCountry(query);
                        break;
                    case R.id.category_radioBtn:
                        searchPresenter.getMealsByCategory(query);
                        break;
                    case R.id.ingredient_radioBtn:
                        searchPresenter.getMealsByIngredient(query);
                        break;
                    default:
                        break;
                }
            }
        });

    }

    void initializeViews(View view) {
        radioGroup = view.findViewById(R.id.radioGroup);
        searchET = view.findViewById(R.id.etSearch);
        searchRecycler = view.findViewById(R.id.searchRecycler);
        searchRecyclerAdapter = new SearchRecyclerAdapter(view.getContext(), this);
        searchRecycler.setAdapter(searchRecyclerAdapter);
        selectedBtnId = R.id.country_radioBtn;
        nullTextView = view.findViewById(R.id.nullTextViewInsearch);
    }

    @Override
    public void onGetMeals(List<Meal> meals) {
        searchRecycler.setVisibility(View.VISIBLE);
        nullTextView.setVisibility(View.GONE);
        searchRecyclerAdapter.setList((ArrayList<Meal>) meals);
        searchRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetAllCategories(List<Category> categories) {

    }

    @Override
    public void onGetAllIngredient(List<Ingredient> ingredients) {

    }

    @Override
    public void onGetAllCountries(List<Country> countries) {

    }

    @Override
    public void onFailureResult(String message) {
        nullTextView.setVisibility(View.VISIBLE);
        searchRecycler.setVisibility(View.GONE);
    }

    @Override
    public void onSaveBtnClicked(Meal meal) {
        searchPresenter.insertMeal(meal);
    }

    @Override
    public void onItemClicked(String id) {
        SearchFragmentDirections.ActionSearchFragmentToDetailsFragment2 action =
                SearchFragmentDirections.actionSearchFragmentToDetailsFragment2(id);
        Navigation.findNavController(getView()).navigate(action);
    }
}