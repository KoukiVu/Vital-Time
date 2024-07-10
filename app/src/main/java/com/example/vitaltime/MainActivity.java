package com.example.vitaltime;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.vitaltime.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import com.google.firebase.FirebaseApp;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity
     {
         private boolean isInitialTrigger = true;
        private ThemeViewModel themeViewModel;
        private AppBarConfiguration appBarConfiguration;
        private ActivityMainBinding binding;
        BottomNavigationView bottomNavigationView;
        public static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        themeViewModel = new ViewModelProvider(this).get(ThemeViewModel.class);
        themeViewModel.getCurrentTheme().observe(this, this::onThemeChanged);
        applyTheme();
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void applyTheme() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDarkTheme = prefs.getBoolean("is_dark_theme", false);
        int themeId = isDarkTheme ? R.style.Theme_VitalTime_Dark : R.style.Theme_VitalTime;
        setTheme(themeId);
        AppCompatDelegate.setDefaultNightMode(isDarkTheme ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        themeViewModel.setTheme(themeId);
    }

         private void onThemeChanged(Integer themeId) {
             if (isInitialTrigger) {
                 isInitialTrigger = false;
                 return;
             }
             if (themeId != null) {
                 recreate();
             }
         }
}

