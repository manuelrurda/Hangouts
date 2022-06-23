package com.example.hangouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hangouts.fragments.LoginFragment;
import com.example.hangouts.databinding.ActivityLoginBinding;

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