package com.example.yumfit.details.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.yumfit.R;
import com.example.yumfit.authentication.register.RegisterActivity;
import com.example.yumfit.db.ConcreteLocalSource;
import com.example.yumfit.db.LocalSource;
import com.example.yumfit.details.presenter.DetailsPresenter;
import com.example.yumfit.details.presenter.DetailsPresenterInterface;
import com.example.yumfit.network.ClientService;
import com.example.yumfit.network.RemoteSource;
import com.example.yumfit.pojo.IngredientPojo;
import com.example.yumfit.pojo.Meal;
import com.example.yumfit.pojo.Repo;
import com.example.yumfit.pojo.RepoInterface;
import com.example.yumfit.ui.Home2Activity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DetailsFragment extends Fragment implements DetailsViewInterface {

    ImageView mealImg;
    TextView mealNameTV, mealCountryTV, mealDescriptionTV;
    Button addToPlanBtn, addToYourCalender, addToFavourite;
    RecyclerView ingredientRecyclerView;
    YouTubePlayerView youTubePlayer;
    String[] videoArray;
    String videoString;
    IngredientRecyclerAdapter ingredientAdapter;
    DetailsPresenterInterface detailsPresenter;
    String id;
    Meal currentMeal;
    FirebaseUser currentUser;
    int mSelectedIndex;

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
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);
        RemoteSource remoteSource = ClientService.getInstance(view.getContext());
        LocalSource localSource = ConcreteLocalSource.getInstance(view.getContext());
        RepoInterface repo = Repo.getInstance(remoteSource, localSource);
        detailsPresenter = new DetailsPresenter(repo, this);

        //this when i come to this fragment start to send request to fetch all data about this id
        id = DetailsFragmentArgs.fromBundle(getArguments()).getId();

        detailsPresenter.getMealById(id);
        addToPlanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    detailsPresenter.insertMealToFavourite(currentMeal);
                    showDialog();
                } else {
                    showMaterialDialog(view.getContext());
                }
            }
        });

        addToFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    detailsPresenter.insertMealToFavourite(currentMeal);
                    Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    showMaterialDialog(view.getContext());
                }
            }
        });


        addToYourCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToMobileCalender();
            }
        });
    }

    private void showDialog() {
        List<String> daysOfWeek = Arrays.asList("SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_single_choice, daysOfWeek);

        new AlertDialog.Builder(getContext())
                .setTitle("Select a day of the week")
                .setSingleChoiceItems(adapter, 0, (dialog, which) -> mSelectedIndex = which)
                .setPositiveButton("OK", (dialog, which) -> {
                    if (mSelectedIndex >= 0) {
                        String selectedDay = daysOfWeek.get(mSelectedIndex);
                        detailsPresenter.updateDayOfMeal(currentMeal.getIdMeal(), selectedDay.toLowerCase());
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void addToMobileCalender() {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, currentMeal.getStrMeal())
                .putExtra(CalendarContract.Events.DESCRIPTION, "Enjoy a delicious " + currentMeal.getStrMeal() + " for dinner!")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Home")
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, System.currentTimeMillis() + (60 * 60 * 1000)); // End time is 1 hour after start time
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Home2Activity) requireActivity()).bottomNavigationView.setVisibility(View.GONE);
    }

    private void initializeViews(View view) {
        mealImg = view.findViewById(R.id.detailsImageView);
        mealNameTV = view.findViewById(R.id.detailsMealNameTextView);
        mealCountryTV = view.findViewById(R.id.detailsCountryName);
        mealDescriptionTV = view.findViewById(R.id.detailsDescriptionOfmeal);
        addToPlanBtn = view.findViewById(R.id.detailsAddToPlanBtn);
        addToYourCalender = view.findViewById(R.id.addToYourCalender);
        addToFavourite = view.findViewById(R.id.detailsAddToFav);
        ingredientRecyclerView = view.findViewById(R.id.detailsIngredientRecycler);
        youTubePlayer = view.findViewById(R.id.youtubePlayer);
        ingredientAdapter = new IngredientRecyclerAdapter(view.getContext());

        ingredientRecyclerView.setAdapter(ingredientAdapter);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

    }

    @Override
    public void onGetMealDetails(List<Meal> meals) {
        currentMeal = meals.get(0);
        Glide.with(getContext()).load(meals.get(0).getStrMealThumb())
                .apply(new RequestOptions().override(500, 500)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.back_register)).into(mealImg);
        mealNameTV.setText(meals.get(0).getStrMeal());
        mealCountryTV.setText(meals.get(0).getStrArea());
        mealDescriptionTV.setText(meals.get(0).getStrInstructions());

        if (!meals.get(0).getStrYoutube().equals("")) {
            videoArray = meals.get(0).getStrYoutube().split("=");
            videoString = videoArray[1];
        } else {
            videoString = "";
        }
        youTubePlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                youTubePlayer.loadVideo(videoString, 0);
                youTubePlayer.pause();
            }
        });
        ArrayList<IngredientPojo> ingredientPojos = getIngredientPojoList(meals.get(0));
        ingredientAdapter.setList(ingredientPojos);
        ingredientAdapter.notifyDataSetChanged();
    }


    @Override
    public void insertMealToFavourite(Meal meal) {
        detailsPresenter.insertMealToFavourite(meal);
    }

    @Override
    public void onFailToGetMealDetails(String message) {
        //tell user there is an error while fetch data
    }


    private ArrayList<IngredientPojo> getIngredientPojoList(Meal meal) {
        ArrayList<IngredientPojo> myIngredientList = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            String ingredient = null;
            try {
                ingredient = (String) meal.getClass().getMethod("getStrIngredient" + i).invoke(meal);

                String measure = (String) meal.getClass().getMethod("getStrMeasure" + i).invoke(meal);

                if (ingredient != null && !ingredient.isEmpty() && measure != null && !measure.isEmpty()) {
                    String imageUrl = "https://www.themealdb.com/images/ingredients/" + ingredient + ".png";
                    myIngredientList.add(new IngredientPojo(ingredient, measure, imageUrl));
                }
            } catch (IllegalAccessException e) {
                //throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                //throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                //throw new RuntimeException(e);
            }
        }

        return myIngredientList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((Home2Activity) requireActivity()).bottomNavigationView.setVisibility(View.VISIBLE);
    }

    private void showMaterialDialog(Context context) {

        new MaterialAlertDialogBuilder(context)
                .setTitle(getResources().getString(R.string.yumfit))
                .setMessage(getResources().getString(R.string.messagePlan))
                .setNegativeButton(getResources().getString(R.string.signIn), (dialog, which) -> {

                    Intent intent = new Intent();
                    intent.setClass(getContext(), RegisterActivity.class);
                    startActivity(intent);
                })
                .setPositiveButton(getResources().getString(R.string.cancel), (dialog, which) -> {


                })
                .show();
    }


}