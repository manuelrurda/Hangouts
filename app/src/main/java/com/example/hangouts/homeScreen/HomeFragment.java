package com.example.hangouts.homeScreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hangouts.R;
import com.example.hangouts.databinding.FragmentHomeBinding;
import com.example.hangouts.models.UserUiModel;
import com.example.hangouts.homeScreen.hangoutCreation.HangoutLocationSelectionMapFragment;
import com.parse.ParseUser;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private final UserUiModel currentUser;

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

        binding.tvUserName.setText(String.format("%s %s.",
                currentUser.getName(),
                currentUser.getLastInitial()));

        binding.btnHomeCreate.setOnClickListener(this::onClickCreate);

        initActiveHangoutsRV();
        initPastHangoutsRV();
    }

    private void initActiveHangoutsRV(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rvActiveHangouts.setLayoutManager(linearLayoutManager);
    }

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