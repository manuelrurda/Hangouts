package com.example.hangouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.hangouts.Fragments.LoginFragment;
import com.example.hangouts.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private FragmentContainerView fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        fragmentContainer = binding.fragmentContainer;

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final Fragment loginFragment = new LoginFragment();
        replaceFragment(fragmentManager, fragmentContainer, loginFragment);

    }

    private void replaceFragment(FragmentManager fragmentManager,
                                 FragmentContainerView fragmentContainer, Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(fragmentContainer.getId(), fragment)
                .commit();
    }
}