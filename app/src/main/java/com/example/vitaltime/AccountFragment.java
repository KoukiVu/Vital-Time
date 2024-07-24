package com.example.vitaltime;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.fragment.NavHostFragment;
import com.example.vitaltime.Login.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;


public class AccountFragment extends BaseFragment {
    Button logoutBtn, resetPwBtn, deleteBtn;
    FirebaseAuth mAuth;
    TextView mUsername;
    String username;


    public AccountFragment() {
        // Required empty public constructor
    }


    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            username = mAuth.getCurrentUser().getEmail();
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        mUsername = view.findViewById(R.id.username);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        resetPwBtn = view.findViewById(R.id.pwResetBtn);
        deleteBtn = view.findViewById(R.id.deleteAccountBtn);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        mUsername.setText(username);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog(AccountFragment.this);
            }
        });
        resetPwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AccountFragment.this).navigate(R.id.resetPasswordFragment);
            }
        });
    }
}