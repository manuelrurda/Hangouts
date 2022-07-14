package com.example.hangouts.onboardingScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.hangouts.databinding.ActivityOnboardingBinding;
import com.parse.ParseUser;

public class OnboardingActivity extends AppCompatActivity {

    private ActivityOnboardingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOnboardingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    // Temporary logout
    public void onClickLogout(View view){
        ParseUser.logOutInBackground();
    }
}