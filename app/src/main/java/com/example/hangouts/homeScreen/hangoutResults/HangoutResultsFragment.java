package com.example.hangouts.homeScreen.hangoutResults;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hangouts.R;
import com.example.hangouts.databinding.FragmentJoinHangoutBinding;

public class HangoutResultsFragment extends Fragment {

    FragmentJoinHangoutBinding binding;

    public HangoutResultsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentJoinHangoutBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


}