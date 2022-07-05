package com.example.hangouts.loginScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hangouts.databinding.FragmentLoginBinding;
import com.example.hangouts.homeScreen.MainActivity;
import com.example.hangouts.models.User;
import com.example.hangouts.onboardingScreen.OnboardingActivity;
import com.example.hangouts.R;
import com.google.android.material.textfield.TextInputEditText;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";
    FragmentLoginBinding binding;

    private final LoginActivity activity = (LoginActivity) getActivity();

    private TextInputEditText itLoginUsername;
    private TextInputEditText itLoginPassword;
    private Button btnLogin;
    private TextView tvLoginSignup;

    public LoginFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itLoginUsername = binding.itLoginUsername;
        itLoginPassword = binding.itLoginPassword;

        tvLoginSignup = binding.tvLoginSignup;
        tvLoginSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.loginFragmentContainer, new SignupFragment())
                        .addToBackStack("")
                        .commit();
            }
        });

        btnLogin = binding.btnLogin;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = itLoginUsername.getText().toString();
                final String password = itLoginPassword.getText().toString();
                loginUser(username, password);
            }
        });
    }

    private void loginUser(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Error in login: ", e);
                    Toast.makeText(getContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!user.getBoolean(User.KEY_ONBOARDINGCOMPLETED)){
                    goOnboardingActivity();
                }else{
                    goMainActivity();
                }
//                getActivity().finish();
            }
        });
    }

    public void goOnboardingActivity(){
        Intent intent = new Intent(getContext(), OnboardingActivity.class);
        startActivity(intent);
    }

    public void goMainActivity(){
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}