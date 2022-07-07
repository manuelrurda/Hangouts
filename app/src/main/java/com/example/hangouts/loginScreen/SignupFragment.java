package com.example.hangouts.loginScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.hangouts.databinding.FragmentSignupBinding;
import com.example.hangouts.onboardingScreen.OnboardingActivity;
import com.google.android.material.textfield.TextInputEditText;

public class SignupFragment extends Fragment {

    private static final String TAG = "SignupFragment";
    FragmentSignupBinding binding;

    private TextInputEditText itName;
    private TextInputEditText itLastName;
    private TextInputEditText itUsername;
    private TextInputEditText itPassword;

    private Button btnSignup;
    private SignupViewModel model = null;

    public SignupFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(SignupViewModel.class);
        model.actions.observe(this, this::handleAction);
    }

    private void handleAction(SignupViewModel.Actions actions) {
        switch (actions) {
            case TOAST_ALL_FIELDS_REQUIRED:
                Toast.makeText(
                        getContext(), "All fields are required", Toast.LENGTH_LONG).show();
                break;
            case TOAST_ERROR_SIGNING_UP:
                Toast.makeText(
                        getContext(), "Error signing up, try different username",
                        Toast.LENGTH_LONG).show();
                break;
            case NAVIGATE_TO_ONBOARDING_ACTIVITY:
                goOnboardingActivity();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itName = binding.itName;
        itLastName = binding.itName;
        itUsername = binding.itSignupUsername;
        itPassword = binding.itSignupPassword;

        btnSignup = binding.btnSignup;
        btnSignup.setOnClickListener(view1 -> {
            final String name = itName.getText().toString();
            final String lastName = itLastName.getText().toString();
            final String username = itUsername.getText().toString();
            final String password = itPassword.getText().toString();
            model.onSignupClick(name, lastName, username, password);
        });
    }

    private void goOnboardingActivity() {
        Intent intent = new Intent(getContext(), OnboardingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
