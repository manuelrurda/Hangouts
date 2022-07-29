package com.example.hangouts.loginScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.transition.TransitionInflater;

import com.example.hangouts.R;
import com.example.hangouts.databinding.ActivityLoginBinding;
import com.example.hangouts.homeScreen.MainActivity;
import com.example.hangouts.models.User;
import com.parse.ParseUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if(ParseUser.getCurrentUser() != null &&
                ParseUser.getCurrentUser().getBoolean(User.KEY_ONBOARDINGCOMPLETED)){
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Initial fragment
        getSupportFragmentManager().beginTransaction()
                .replace(binding.loginFragmentContainer.getId(), new LoginFragment())
                .commit();


    }
}