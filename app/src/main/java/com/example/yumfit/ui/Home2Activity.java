package com.example.yumfit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.yumfit.R;
import com.example.yumfit.favourite.view.FavouriteFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home2Activity extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;
    public NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        initialiseViews();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        if (!checkConnection()) {
            FavouriteFragment fragment = new FavouriteFragment();
            fragment.setArguments(new Bundle());
            navController.navigate(R.id.favouriteFragment);
            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem = menu.findItem(R.id.homeFragment);
            MenuItem menuItem2 =menu.findItem(R.id.searchFragment);
            MenuItem menuItem3 =menu.findItem(R.id.profileFragment);
            menuItem.setEnabled(false);
            menuItem2.setEnabled(false);
            menuItem3.setEnabled(false);
        }

    }


    private void initialiseViews() {

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

    }

    private boolean checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        boolean isConnected = networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        return isConnected;
    }
}