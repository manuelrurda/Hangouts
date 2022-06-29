package com.example.hangouts.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hangouts.R;
import com.example.hangouts.adapters.PreferenceCardAdapter;
import com.example.hangouts.databinding.FragmentDragDropCuisineBinding;
import com.example.hangouts.models.PreferenceCard;
import com.example.hangouts.viewmodels.DragDropCuisineViewModel;

import java.util.List;

public class DragDropCuisineFragment extends Fragment {

    private FragmentDragDropCuisineBinding binding;
    private DragDropCuisineViewModel dragDropCuisineViewModel;

    private RecyclerView rvPreferenceCards;
    private PreferenceCardAdapter adapter;

    public DragDropCuisineFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDragDropCuisineBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView();

        dragDropCuisineViewModel = new ViewModelProvider(this).get(DragDropCuisineViewModel.class);
        dragDropCuisineViewModel.init();
        dragDropCuisineViewModel.getCuisinePreferenceCards().observe(getViewLifecycleOwner(),
                new Observer<List<PreferenceCard>>() {
                    @Override
                    public void onChanged(List<PreferenceCard> preferenceCards) {
                        adapter.setPreferenceCards(preferenceCards);
                    }
                });
    }

    private void initRecyclerView() {
        rvPreferenceCards = binding.rvPreferenceCards;
        rvPreferenceCards.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPreferenceCards.setHasFixedSize(true);
        adapter = new PreferenceCardAdapter(getContext());
        rvPreferenceCards.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}