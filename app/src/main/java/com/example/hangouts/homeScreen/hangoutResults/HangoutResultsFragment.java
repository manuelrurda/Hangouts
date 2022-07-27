package com.example.hangouts.homeScreen.hangoutResults;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hangouts.R;
import com.example.hangouts.databinding.FragmentHangoutResultsBinding;
import com.example.hangouts.databinding.FragmentJoinHangoutBinding;
import com.example.hangouts.homeScreen.HangoutDetailsFragment;
import com.example.hangouts.homeScreen.hangoutCreation.CreateHangoutViewModel;
import com.example.hangouts.models.Hangout;

public class HangoutResultsFragment extends Fragment {

    private FragmentHangoutResultsBinding binding;
    private Hangout hangout;

    private HangoutResultsViewModel viewModel;

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
        binding = FragmentHangoutResultsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(HangoutResultsViewModel.class);
        viewModel.generateFrequencyMatrix(hangout);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}