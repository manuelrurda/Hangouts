package com.example.hangouts.onboarding;

import androidx.lifecycle.MutableLiveData;

import com.example.hangouts.onboarding.models.DropZone;
import com.example.hangouts.onboarding.models.PreferenceCard;

import java.util.ArrayList;
import java.util.List;

// Singleton
public class CuisinePreferenceCardRepository {

    private static CuisinePreferenceCardRepository instance;
    private ArrayList<PreferenceCard> preferenceCards = new ArrayList<>();
    private ArrayList<DropZone> dropZones = new ArrayList<>();

    public static CuisinePreferenceCardRepository getInstance(){
        if (instance == null){
            instance = new CuisinePreferenceCardRepository();
        }
        return instance;
    }

    public MutableLiveData<List<PreferenceCard>> getPreferenceCards(){
        setCuisineTypes();
        MutableLiveData<List<PreferenceCard>> data = new MutableLiveData<>();
        data.setValue(preferenceCards);

        return data;
    }

//    public MutableLiveData<List<DropZone>> getDropZones(){
//        setDropZones();
//        MutableLiveData<List<DropZone>> data = new MutableLiveData<>();
//        data.setValue(dropZones);
//        return data;
//    }

    private void setCuisineTypes(){
        preferenceCards.add(new PreferenceCard("Italian"));
        preferenceCards.add(new PreferenceCard("Mexican"));
        preferenceCards.add(new PreferenceCard("Chinese"));
        preferenceCards.add(new PreferenceCard("Japanese"));
        preferenceCards.add(new PreferenceCard("Thai"));
        preferenceCards.add(new PreferenceCard("Spanish"));
        preferenceCards.add(new PreferenceCard("Indian"));
        preferenceCards.add(new PreferenceCard("Greek"));
        preferenceCards.add(new PreferenceCard("French"));
    }
}