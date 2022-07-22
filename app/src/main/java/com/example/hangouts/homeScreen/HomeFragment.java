package com.example.hangouts.homeScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hangouts.HomeViewModel;
import com.example.hangouts.R;
import com.example.hangouts.databinding.FragmentHomeBinding;
import com.example.hangouts.homeScreen.hangoutJoining.JoinHangoutFragment;
import com.example.hangouts.loginScreen.LoginActivity;
import com.example.hangouts.models.Hangout;
import com.example.hangouts.models.UserUiModel;
import com.example.hangouts.homeScreen.hangoutCreation.HangoutLocationSelectionMapFragment;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

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

        initActiveHangoutsRV();
        initPastHangoutsRV();
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.getActiveHangouts();
        homeViewModel.activeHangouts.observe(getViewLifecycleOwner(), this::updateActiveHangouts);
        binding.tvUserName.setText(String.format("%s %s.",
                currentUser.getName(),
                currentUser.getLastInitial()));

        binding.btnHomeCreate.setOnClickListener(this::onClickCreate);
        binding.btnHomeJoin.setOnClickListener(this::onClickJoin);
        binding.btnHomeLogout.setOnClickListener(this::onClickLogout);
    }

    private void onClickLogout(View view) {
        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Toast.makeText(getContext(), "Error Logging Out", Toast.LENGTH_SHORT)
                            .show();
                }else{
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
    }

    private void updateActiveHangouts(List<Hangout> hangouts) {
        activeHangoutsAdapter = new ActiveHangoutsAdapter(getContext());
        binding.rvActiveHangouts.setAdapter(activeHangoutsAdapter);
        activeHangoutsAdapter.setActiveHangouts(hangouts);
    }

    private void initActiveHangoutsRV(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rvActiveHangouts.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding
                .rvActiveHangouts.getContext(), linearLayoutManager.getOrientation());
        binding.rvActiveHangouts.addItemDecoration(dividerItemDecoration);
    }

    private void initPastHangoutsRV(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rvPastHangouts.setLayoutManager(linearLayoutManager);
    }

    private void onClickCreate(View view) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.homeFragmentContainer, new HangoutLocationSelectionMapFragment())
                .addToBackStack("home")
                .commit();
    }

    private void onClickJoin(View view) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.homeFragmentContainer, new JoinHangoutFragment())
                .addToBackStack("home")
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}