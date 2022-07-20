package com.example.hangouts.homeScreen.hangoutJoining;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hangouts.R;
import com.example.hangouts.homeScreen.HangoutDetailsFragment;
import com.example.hangouts.homeScreen.hangoutJoining.JoinHangoutViewModel.Errors;
import com.example.hangouts.databinding.FragmentJoinHangoutBinding;
import com.example.hangouts.models.Hangout;

public class JoinHangoutFragment extends Fragment {

    FragmentJoinHangoutBinding binding;
    JoinHangoutViewModel viewModel;

    public JoinHangoutFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentJoinHangoutBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(JoinHangoutViewModel.class);
        viewModel.errors.observe(getViewLifecycleOwner(), this::handleError);
        viewModel.toJoinHangout.observe(getViewLifecycleOwner(), this::hangoutFound);
        viewModel.joinedHangout.observe(getViewLifecycleOwner(), this::joinedHanogut);
        binding.btnOkJoinHangout.setOnClickListener(this::findHanogut);
    }

    private void joinedHanogut(Hangout hangout) {
        FragmentManager fm = getParentFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount()-1; ++i) {
            fm.popBackStack();
        }
        fm.beginTransaction()
                .replace(R.id.homeFragmentContainer, HangoutDetailsFragment.newInstance(hangout))
                .commit();
    }

    private void hangoutFound(Hangout hangout) {
        viewModel.joinHangout(hangout);
    }

    private void handleError(Errors error) {
        switch (error){
            case ERROR_EMPTY_HANGOUTID_STRING:
                Toast.makeText(getContext(), "CODE CAN NOT BE EMPTY", Toast.LENGTH_SHORT)
                        .show();
            break;
            case ERROR_HANGOUT_NOT_FOUND:
                Toast.makeText(getContext(), "HANGOUT NOT FOUND", Toast.LENGTH_SHORT)
                        .show();
            break;
            case ERROR_USER_ALREADY_IN_HANGOUT:
                Toast.makeText(getContext(), "USER ALREADY IN HANGOUT", Toast.LENGTH_SHORT)
                        .show();
            break;
            case ERROR_UPDATING_HANGOUT:
                Toast.makeText(getContext(), "ERROR JOINING HANGOUT", Toast.LENGTH_SHORT)
                        .show();
            break;
            case ERROR_ADDING_HANGOUT_TO_USER:
                Toast.makeText(getContext(), "ERROR UPDATING USER", Toast.LENGTH_SHORT)
                        .show();
            break;
        }
    }

    private void findHanogut(View view) {
        String hangoutId = "";
        Editable hangoutIdEditable = binding.itJoinFragmentCode.getText();
        if(hangoutIdEditable != null){
            hangoutId = hangoutIdEditable.toString();
        }
        viewModel.findHangout(hangoutId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.errors.removeObservers(getViewLifecycleOwner());
        viewModel.toJoinHangout.removeObservers(getViewLifecycleOwner());
        binding = null;
    }
}