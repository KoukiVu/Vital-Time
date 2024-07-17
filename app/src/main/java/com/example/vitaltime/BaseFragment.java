package com.example.vitaltime;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
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
            showLogoutDialog(this);
            return true;
        } else if (item.getItemId() == R.id.themes) {
            showThemesDialog(this);
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }


    // Dialog View Methods
    private void showThemesDialog(Fragment parent) {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_themes, null);
        final int[] selectedTheme = {-1};
        Button confirmBtn = dialogView.findViewById(R.id.confirm_button);
        CardView lightTheme = dialogView.findViewById(R.id.lightCard);
        CardView darkTheme = dialogView.findViewById(R.id.darkCard);
        CardView pastelTheme = dialogView.findViewById(R.id.pastelCard);
        CardView coffeeTheme = dialogView.findViewById(R.id.coffeeCard);
        CardView midnightTheme = dialogView.findViewById(R.id.midnightCard);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(dialogView);
        builder.setTitle("Themes");
        android.app.AlertDialog alertDialog = builder.create();

        // Sets theme for each selection
        lightTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTheme[0] = R.style.Theme_VitalTime;
            }
        });
        darkTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTheme[0] = R.style.Theme_VitalTime_Dark;
            }
        });
        pastelTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTheme[0] = R.style.Theme_VitalTime_Pastel;
            }
        });
        coffeeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTheme[0] = R.style.Theme_VitalTime_Coffee;
            }
        });
        midnightTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTheme[0] = R.style.Theme_VitalTime_Midnight;
            }
        });

        // Confirm button changes the theme according to selection
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTheme[0] == -1) {
                    alertDialog.dismiss();
                } else
                {
                    themeViewModel.setTheme(selectedTheme[0]);
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
                    prefs.edit().putInt("theme", selectedTheme[0]).apply();
                    alertDialog.dismiss();
                }
            }
        });

        alertDialog.show();
    }

    private void showLogoutDialog(Fragment parent) {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_logout, null);
        Button logoutBtn = dialogView.findViewById(R.id.confirm_button);
        Button cancelBtn = dialogView.findViewById(R.id.cancel_button);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(dialogView);
        builder.setTitle("Logout");
        android.app.AlertDialog alertDialog = builder.create();
        //confirmation logs out user
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                NavHostFragment.findNavController(parent).navigate(R.id.loginFragment);
                alertDialog.dismiss();}
        });
        // cancel closes the dialog
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();}
        });
        alertDialog.show();
    }
}