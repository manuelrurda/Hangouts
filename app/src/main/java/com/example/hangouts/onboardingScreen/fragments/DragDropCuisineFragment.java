package com.example.hangouts.onboardingScreen.fragments;

import android.content.ClipDescription;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.hangouts.databinding.FragmentDragDropCuisineBinding;
import com.example.hangouts.homeScreen.MainActivity;
import com.example.hangouts.models.User;
import com.example.hangouts.onboardingScreen.DragDropCuisineViewModel;
import com.example.hangouts.onboardingScreen.DropZoneAdapter;
import com.example.hangouts.onboardingScreen.PreferenceCardView;
import com.example.hangouts.onboardingScreen.models.DropZone;
import com.example.hangouts.onboardingScreen.models.PreferenceCard;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DragDropCuisineFragment extends Fragment {

    public static final String TAG = "DnDFragment";

    private FragmentDragDropCuisineBinding binding;
    private DragDropCuisineViewModel dragDropCuisineViewModel;
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
        binding.clParentLayout.setOnDragListener((v, event) ->{
            switch (event.getAction()){
                case DragEvent.ACTION_DRAG_STARTED:
                    return event.getClipDescription().hasMimeType(
                            ClipDescription.MIMETYPE_TEXT_PLAIN);
                case DragEvent.ACTION_DRAG_ENTERED:
                case DragEvent.ACTION_DRAG_EXITED:
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DROP:
                    restoreCard();
                case DragEvent.ACTION_DRAG_LOCATION:
                    return true;
            }
            return true;
        });
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMainActivity();
                setUserPreferences();
            }
        });
    }

    private void setUserPreferences() {
        JSONObject cuisinePreferences = new JSONObject();
        for(DropZone dropZone : dropZones){
            for (String cuisine : dropZone.getContent()){
                try {
                    cuisinePreferences.put(cuisine, dropZone.getDropValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.put(User.KEY_CUSINEPREFERENCES, cuisinePreferences);
        currentUser.put(User.KEY_ONBOARDINGCOMPLETED, true);
        currentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Log.e(TAG, "done: Error saving user's preferences", e);
                }
            }
        });
    }

    private void goMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    // TODO: handle drag and drop between dropzones
    private void restoreCard() {
        PreferenceCard preferenceCard = dragDropCuisineViewModel.getTopCard();
        binding.flPreferenceCardContainer.removeViewAt(0);
        binding.flPreferenceCardContainer.addView(new PreferenceCardView(getContext(), preferenceCard.getValue()));
    }

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
        binding.rvDropZones.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.rvDropZones.setHasFixedSize(true);
        dropZoneAdapter = new DropZoneAdapter(getContext(), dropZones, dragDropCuisineViewModel);
        binding.rvDropZones.setAdapter(dropZoneAdapter);
    }

    private void initViewModel() {
        dragDropCuisineViewModel = new ViewModelProvider(getActivity()).get(DragDropCuisineViewModel.class);
        dragDropCuisineViewModel.init();
        dragDropCuisineViewModel.getCuisinePreferenceCards().observe(getViewLifecycleOwner(),
                new Observer<List<PreferenceCard>>() {
            // TODO: Undo button, handle 'next' button when undo las element
                    @Override
                    public void onChanged(List<PreferenceCard> preferenceCards) {
                        if(preferenceCards.isEmpty()){
                            showNextButton();
                        }else{
                            PreferenceCard nextCard = preferenceCards.get(0);
                            binding.flPreferenceCardContainer.addView(new PreferenceCardView(getContext(),
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
        binding.btnNext.startAnimation(anim);
        binding.btnNext.setVisibility(View.VISIBLE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}