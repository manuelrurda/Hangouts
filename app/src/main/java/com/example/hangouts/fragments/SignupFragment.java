package com.example.hangouts.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.hangouts.databinding.FragmentSignupBinding;
import com.example.hangouts.models.User;
import com.google.android.material.textfield.TextInputEditText;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupFragment extends Fragment {

    private static final String TAG = "SignupFragment";
    FragmentSignupBinding binding;

    private TextInputEditText itName;
    private TextInputEditText itLastName;
    private TextInputEditText itUsername;
    private TextInputEditText itPassword;

    private Button btnSignup;

    public SignupFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_signup, container, false);

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
        btnSignup.setOnClickListener(new View.OnClickListener() {
            final String name = itName.getText().toString();
            final String lastName = itLastName.getText().toString();
            final String username = itUsername.getText().toString();
            final String password = itPassword.getText().toString();
            @Override
            public void onClick(View v) {
                if(name.isEmpty() || lastName.isEmpty() ||
                        username.isEmpty() || password.isEmpty()){
                    Toast.makeText(
                            getContext(), "All fields are required", Toast.LENGTH_LONG).show();
                }else{
                    signupUser(name, lastName, username, password);
                }
            }
        });
    }

    private void signupUser(String name, String lastName, String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setUsername(password);
        user.put(User.KEY_NAME, name);
        user.put(User.KEY_LASTNAME, lastName);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Log.e(TAG, "Error signing up user: ", e);
                }else{

                }
            }
        });

    }
}