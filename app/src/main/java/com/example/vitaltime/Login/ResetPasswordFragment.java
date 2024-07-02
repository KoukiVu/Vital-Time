package com.example.vitaltime.Login;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.fragment.NavHostFragment;
import com.example.vitaltime.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import kotlin.OverloadResolutionByLambdaReturnType;
import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResetPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResetPasswordFragment extends Fragment {

    Button emailBtn;
    private TextInputEditText editTextEmail;
    FirebaseAuth mAuth;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ResetPasswordFragment.
     */

    public static ResetPasswordFragment newInstance(String param1, String param2) {
        ResetPasswordFragment fragment = new ResetPasswordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        editTextEmail = view.findViewById(R.id.email);
        emailBtn = view.findViewById(R.id.resetBtn);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password, confirmPassword;
                email = String.valueOf(editTextEmail.getText());

                // error message if a field is left empty
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getActivity(), "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }


                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "Email sent", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                // Email doesn't belong to a user
                                Toast.makeText(getContext(), "No account found with this email", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                // Other errors
                                Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

            }
        });
    }
}