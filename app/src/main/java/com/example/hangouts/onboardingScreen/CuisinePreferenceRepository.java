package com.example.hangouts.onboardingScreen;

import androidx.lifecycle.MutableLiveData;

import com.example.hangouts.onboardingScreen.models.PreferenceCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Singleton
public class CuisinePreferenceRepository {

    public static final List<String> CUISINE_TYPES = Arrays.asList(
            "Italian",
            "Mexican",
            "Chinese",
            "Japanese",
            "Thai",
            "Spanish",
            "Indian",
            "Greek",
            "French"
    );

    private static CuisinePreferenceRepository instance;
    private ArrayList<PreferenceCard> preferenceCards = new ArrayList<>();

    public static CuisinePreferenceRepository getInstance(){
        if (instance == null){
            instance = new CuisinePreferenceRepository();
        }
        return instance;
    }

    public MutableLiveData<List<PreferenceCard>> getPreferenceCards(){
        setCuisineTypes();
        MutableLiveData<List<PreferenceCard>> data = new MutableLiveData<>();
        data.setValue(preferenceCards);

        return data;
    }

    private void setCuisineTypes(){
        for (String cuisineType:CUISINE_TYPES) {
            preferenceCards.add(new PreferenceCard(cuisineType));
        }
    }
}