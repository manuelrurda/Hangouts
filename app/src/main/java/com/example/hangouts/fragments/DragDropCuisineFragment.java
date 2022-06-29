package com.example.hangouts.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hangouts.adapters.DropZoneAdapter;
import com.example.hangouts.adapters.PreferenceCardAdapter;
import com.example.hangouts.databinding.FragmentDragDropCuisineBinding;
import com.example.hangouts.models.DropZone;
import com.example.hangouts.models.PreferenceCard;
import com.example.hangouts.viewmodels.DragDropCuisineViewModel;

import java.util.ArrayList;
import java.util.List;

public class DragDropCuisineFragment extends Fragment {

    private FragmentDragDropCuisineBinding binding;
    private DragDropCuisineViewModel dragDropCuisineViewModel;

    private RecyclerView rvPreferenceCards;
    private RecyclerView rvDropZones;
//    private ConstraintLayout clDropZoneLayout;
    private DropZoneAdapter dropZoneAdapter;
    private PreferenceCardAdapter preferenceCardAdapter;

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
        initPreferenceCardRV();
        initViewModel();
        initDropZoneRV();
//        initDropZones();
    }

    private void initDropZoneRV() {
        List<DropZone> dropZones = new ArrayList<DropZone>();
        dropZones.add(new DropZone(0, new ArrayList<>()));
        dropZones.add(new DropZone(1, new ArrayList<>()));
        dropZones.add(new DropZone(2, new ArrayList<>()));
        dropZones.add(new DropZone(3, new ArrayList<>()));
        dropZones.add(new DropZone(4, new ArrayList<>()));
        dropZones.add(new DropZone(5, new ArrayList<>()));

        rvDropZones = binding.rvDropZones;
        rvDropZones.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvDropZones.setHasFixedSize(true);
        dropZoneAdapter = new DropZoneAdapter(getContext(), dropZones, dragDropCuisineViewModel);
        rvDropZones.setAdapter(dropZoneAdapter);


    }

    private void initPreferenceCardRV() {
        rvPreferenceCards = binding.rvPreferenceCards;
        rvPreferenceCards.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPreferenceCards.setHasFixedSize(true);
        preferenceCardAdapter = new PreferenceCardAdapter(getContext());
        rvPreferenceCards.setAdapter(preferenceCardAdapter);
    }

    private void initViewModel() {
        dragDropCuisineViewModel = new ViewModelProvider(getActivity()).get(DragDropCuisineViewModel.class);
        dragDropCuisineViewModel.init();
        dragDropCuisineViewModel.getCuisinePreferenceCards().observe(getViewLifecycleOwner(),
                new Observer<List<PreferenceCard>>() {
                    @Override
                    public void onChanged(List<PreferenceCard> preferenceCards) {
                        Toast.makeText(getContext(), "CHANGE OBSERVED", Toast.LENGTH_SHORT).show();
                        preferenceCardAdapter.setPreferenceCards(preferenceCards);
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}