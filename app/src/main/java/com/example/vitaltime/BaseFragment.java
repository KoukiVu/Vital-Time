package com.example.vitaltime;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import org.jetbrains.annotations.NotNull;


public class BaseFragment extends Fragment
implements  BottomNavigationView.OnNavigationItemSelectedListener {
    private ThemeViewModel themeViewModel;
    BottomNavigationView bottomNavigationView;

    public BaseFragment() {
        // Required empty public constructor
    }


    public static BaseFragment newInstance(String param1, String param2) {
        BaseFragment fragment = new BaseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeViewModel = new ViewModelProvider(requireActivity()).get(ThemeViewModel.class);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        return view;
    }

    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.tasks) {
            NavHostFragment.findNavController(this).navigate(R.id.toDoFragment);
            return true;
        } else if (menuItem.getItemId() == R.id.home) {
            NavHostFragment.findNavController(this).navigate(R.id.homeFragment);
            return true;
        } else if (menuItem.getItemId() == R.id.diary) {
            NavHostFragment.findNavController(this).navigate(R.id.journalHome);
            return true;
        } else
            return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        boolean isDarkTheme = prefs.getBoolean("is_dark_theme", false);
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.dark_mode).setChecked(isDarkTheme);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //logout functionality
        if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            NavHostFragment.findNavController(this).navigate(R.id.loginFragment);
            return true;

        // dark mode switch functionality
        } else if (item.getItemId() == R.id.dark_mode){
            boolean darkMode = !item.isChecked();
            item.setChecked(darkMode);
            //applies the appropriate theme based on checkbox
            if (darkMode) {
                themeViewModel.setTheme(R.style.Theme_VitalTime_Dark);
            } else
                themeViewModel.setTheme(R.style.Theme_VitalTime);

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
            prefs.edit().putBoolean("is_dark_theme", darkMode).apply();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }
}