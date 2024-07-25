package com.example.vitaltime;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.fragment.NavHostFragment;
import com.example.vitaltime.Login.LoginFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.*;


public class AccountFragment extends BaseFragment {
    Button logoutBtn, resetPwBtn, deleteBtn;
    FirebaseAuth mAuth;
    TextView mUsername;
    String username;
    AuthCredential credential;


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
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDeleteDialog(AccountFragment.this); }
        });
    }

    public interface ReAuthCallback {
        void onReAuthComplete(AuthCredential credential);
    }

    private void showDeleteDialog(Fragment parent) {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_delete_account, null);
        Button deleteBtn = dialogView.findViewById(R.id.confirm_button);
        Button cancelBtn = dialogView.findViewById(R.id.cancel_button);
        FirebaseUser user = mAuth.getCurrentUser();
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(dialogView);
        builder.setTitle("Delete Account");
        android.app.AlertDialog alertDialog = builder.create();

        //confirmation re-authenticates user and deletes account
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                showReAuthDialog(AccountFragment.this, new ReAuthCallback() {
                    @Override
                    public void onReAuthComplete(AuthCredential credential) {
                        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    alertDialog.dismiss();
                                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getActivity(), "Account deleted successfully!",
                                                        Toast.LENGTH_SHORT).show();
                                                NavHostFragment.findNavController(AccountFragment.this).navigate(R.id.loginFragment);
                                            } else {
                                                Toast.makeText(getActivity(), "Failed to delete account!",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    alertDialog.dismiss();
                                    Toast.makeText(getActivity(), "Failed to confirm account!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }

        });
        // cancel closes the dialog
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();}
        });
        alertDialog.show();
    }

    private void showReAuthDialog(Fragment parent, ReAuthCallback callback) {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_reauth, null);
        TextInputEditText password = dialogView.findViewById(R.id.password);
        TextInputEditText email = dialogView.findViewById(R.id.email);
        Button confirmBtn = dialogView.findViewById(R.id.confirmBtn);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(dialogView);
        builder.setTitle("Confirm Login");
        android.app.AlertDialog alertDialog = builder.create();

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail, mPassword;
                mEmail = String.valueOf(email.getText());
                mPassword = String.valueOf(password.getText());

                // error message if a field is left empty
                if (TextUtils.isEmpty(mEmail)){
                    Toast.makeText(getActivity(), "Please enter email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mPassword)){
                    Toast.makeText(getActivity(), "Please enter password", Toast.LENGTH_SHORT).show();
                } else {
                    credential = EmailAuthProvider.getCredential(mEmail, mPassword);
                    alertDialog.dismiss();
                    callback.onReAuthComplete(credential);
                }
            }
        });
        alertDialog.show();
    }
}