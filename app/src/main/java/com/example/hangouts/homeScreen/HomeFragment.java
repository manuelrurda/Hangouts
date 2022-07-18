package com.example.hangouts.homeScreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hangouts.HomeViewModel;
import com.example.hangouts.R;
import com.example.hangouts.databinding.FragmentHomeBinding;
import com.example.hangouts.homeScreen.hangoutCreation.CreateHangoutViewModel;
import com.example.hangouts.models.Hangout;
import com.example.hangouts.models.UserUiModel;
import com.example.hangouts.homeScreen.hangoutCreation.HangoutLocationSelectionMapFragment;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private final UserUiModel currentUser;
    private HomeViewModel homeViewModel;
    private ActiveHangoutsAdapter activeHangoutsAdapter;

    public HomeFragment() {
        currentUser = new UserUiModel(ParseUser.getCurrentUser());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        initActiveHangoutsRV();
        initPastHangoutsRV();
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.getActiveHangouts();
//        homeViewModel.activeHangouts.observe(requireActivity(), this::updateActiveHangouts);
        binding.tvUserName.setText(String.format("%s %s.",
                currentUser.getName(),
                currentUser.getLastInitial()));

        binding.btnHomeCreate.setOnClickListener(this::onClickCreate);
    }

    private void updateActiveHangouts(List<Hangout> hangouts) {
        activeHangoutsAdapter.setActiveHangouts(hangouts);
    }

//    private void initActiveHangoutsRV(){
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        binding.rvActiveHangouts.setLayoutManager(linearLayoutManager);
//        activeHangoutsAdapter = new ActiveHangoutsAdapter();
//        binding.rvActiveHangouts.setAdapter(activeHangoutsAdapter);
//    }

    private void initPastHangoutsRV(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rvPastHangouts.setLayoutManager(linearLayoutManager);
    }

    private void onClickCreate(View view) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.homeFragmentContainer, new HangoutLocationSelectionMapFragment())
                .addToBackStack("")
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}