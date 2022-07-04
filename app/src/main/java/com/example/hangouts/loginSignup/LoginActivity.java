package com.example.hangouts.loginSignup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import com.example.hangouts.databinding.ActivityLoginBinding;

import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    public FragmentContainerView loginFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Initial fragment
        loginFragmentContainer = binding.loginFragmentContainer;
        getSupportFragmentManager().beginTransaction()
                .replace(loginFragmentContainer.getId(), new LoginFragment())
                .commit();

    }
}