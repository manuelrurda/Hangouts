package com.example.hangouts.homeScreen.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hangouts.R;
import com.example.hangouts.databinding.FragmentHomeBinding;
import com.example.hangouts.homeScreen.UserUI;
import com.example.hangouts.models.User;
import com.parse.ParseUser;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private TextView tvUserName;
    private UserUI currentUser;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentUser = new UserUI(ParseUser.getCurrentUser());

        tvUserName = binding.tvUserName;
        tvUserName.setText(String.format("%s %s.",
                currentUser.getName(),
                currentUser.getLastInitial()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}