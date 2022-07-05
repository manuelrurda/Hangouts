package com.example.hangouts.loginScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import com.example.hangouts.databinding.ActivityLoginBinding;
import com.example.hangouts.homeScreen.MainActivity;
import com.parse.ParseUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    public FragmentContainerView loginFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(ParseUser.getCurrentUser() != null){
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

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