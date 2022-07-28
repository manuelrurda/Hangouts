package com.example.hangouts.onboardingScreen.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionInflater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.example.hangouts.R;
import com.example.hangouts.databinding.FragmentDragDropTutorialBinding;

public class DragDropTutorialFragment extends Fragment {

    public static final String TAG = "TUTORIAL";

    private FragmentDragDropTutorialBinding binding;

    public DragDropTutorialFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.slide_left));
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDragDropTutorialBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        moveAnimation(binding.ivPreferenceCard, Animation.ABSOLUTE, -500);
        moveAnimation(binding.ivHand, Animation.ABSOLUTE, -500);
        binding.btnTutorialNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.onboardingFragmentContainer, new DragDropCuisineFragment())
                        .commit();
            }
        });

    }

    private void moveAnimation(View v, int toX, int toY) {
        Animation animation = new TranslateAnimation(Animation.ABSOLUTE, toX,
                Animation.ABSOLUTE, toY);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setInterpolator(new AnticipateInterpolator(1));
        animation.setStartOffset(500);

        v.startAnimation(animation);
    }
}