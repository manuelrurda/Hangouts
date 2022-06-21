package com.example.hangouts.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hangouts.LoginActivity;
import com.example.hangouts.R;
import com.example.hangouts.databinding.ActivityLoginBinding;
import com.example.hangouts.databinding.FragmentLoginBinding;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;
    ActivityLoginBinding loginBinding;

    private TextInputEditText itLoginUsername;
    private TextInputEditText itLoginPassword;
    private Button btnLogin;
    private TextView tvLoginSignup;

    public LoginFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_login, container, false);
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
                        .replace(R.id.fragmentContainer, new SignupFragment())
                        .addToBackStack("")
                        .commit();
            }
        });


    }
}