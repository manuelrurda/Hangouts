package com.example.hangouts.homeScreen.hangoutResults;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hangouts.R;
import com.example.hangouts.databinding.FragmentJoinHangoutBinding;
import com.example.hangouts.homeScreen.HangoutDetailsFragment;
import com.example.hangouts.models.Hangout;

public class HangoutResultsFragment extends Fragment {

    private FragmentJoinHangoutBinding binding;
    private Hangout hangout;

    public HangoutResultsFragment() {}

    public static HangoutResultsFragment newInstance(Hangout hangout){
        HangoutResultsFragment fragment = new HangoutResultsFragment();
        Bundle args = new Bundle();
        args.putParcelable(Hangout.FRAGMENT_ARGUMENT_ID, hangout);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getArguments().isEmpty()) {
            hangout = getArguments().getParcelable(Hangout.FRAGMENT_ARGUMENT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentJoinHangoutBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}