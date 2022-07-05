package com.example.hangouts.onboardingScreen.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.hangouts.databinding.FragmentDragDropCuisineBinding;
import com.example.hangouts.onboardingScreen.DragDropCuisineViewModel;
import com.example.hangouts.onboardingScreen.DropZoneAdapter;
import com.example.hangouts.onboardingScreen.PreferenceCardView;
import com.example.hangouts.onboardingScreen.models.DropZone;
import com.example.hangouts.onboardingScreen.models.PreferenceCard;

import java.util.ArrayList;
import java.util.List;

public class DragDropCuisineFragment extends Fragment {

    public static final String TAG = "DnDFragment";

    private FragmentDragDropCuisineBinding binding;
    private DragDropCuisineViewModel dragDropCuisineViewModel;
    private FrameLayout flPreferenceCardContainer;
    private Button btnNext;

    private RecyclerView rvDropZones;
    private DropZoneAdapter dropZoneAdapter;

    private List<DropZone> dropZones;

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
        initDropZoneArray();
        initViewModel();
        initDropZoneRV();

        flPreferenceCardContainer = binding.flPreferenceCardContainer;
        btnNext = binding.btnNext;
    }

    // TODO: this should probably be in viewmodel
    private void initDropZoneArray() {
        dropZones = new ArrayList<>();
        dropZones.add(new DropZone(0, new ArrayList<>()));
        dropZones.add(new DropZone(1, new ArrayList<>()));
        dropZones.add(new DropZone(2, new ArrayList<>()));
        dropZones.add(new DropZone(3, new ArrayList<>()));
        dropZones.add(new DropZone(4, new ArrayList<>()));
        dropZones.add(new DropZone(5, new ArrayList<>()));
    }

    private void initDropZoneRV() {
        rvDropZones = binding.rvDropZones;
        rvDropZones.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvDropZones.setHasFixedSize(true);
        dropZoneAdapter = new DropZoneAdapter(getContext(), dropZones, dragDropCuisineViewModel);
        rvDropZones.setAdapter(dropZoneAdapter);
    }

    private void initViewModel() {
        dragDropCuisineViewModel = new ViewModelProvider(getActivity()).get(DragDropCuisineViewModel.class);
        dragDropCuisineViewModel.init();
        dragDropCuisineViewModel.getCuisinePreferenceCards().observe(getViewLifecycleOwner(),
                new Observer<List<PreferenceCard>>() {
            // TODO: Undo button, handle 'next' button when undo las element

            // TODO: Handle card dropped outside any dropzone
                    @Override
                    public void onChanged(List<PreferenceCard> preferenceCards) {
                        if(preferenceCards.isEmpty()){
                            // make next button appear
                            showNextButton();

                        }else{
                            PreferenceCard nextCard = preferenceCards.get(0);
                            flPreferenceCardContainer.addView(new PreferenceCardView(getContext(),
                                    nextCard.getValue()));
                        }
                    }
                });
    }

    private void showNextButton() {
        ScaleAnimation anim = new ScaleAnimation(0f, 1f, 0f,
                1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        int animDurationMs = 200;
        anim.setDuration(animDurationMs);
        anim.setFillAfter(true);
        btnNext.startAnimation(anim);
        btnNext.setVisibility(View.VISIBLE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}