package com.example.hangouts.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.hangouts.models.PreferenceCard;

import java.util.ArrayList;
import java.util.List;

// Singleton
public class CuisinePreferenceCardRepository {

    private static CuisinePreferenceCardRepository instance;
    private ArrayList<PreferenceCard> dataset = new ArrayList<>();

    public static CuisinePreferenceCardRepository getInstance(){
        if (instance == null){
            instance = new CuisinePreferenceCardRepository();
        }
        return instance;
    }

    public MutableLiveData<List<PreferenceCard>> getCuisineTypes(){
        setCuisineTypes();
        MutableLiveData<List<PreferenceCard>> data = new MutableLiveData<>();
        data.setValue(dataset);

        return data;
    }

    private void setCuisineTypes(){
        dataset.add(new PreferenceCard("Italian"));
        dataset.add(new PreferenceCard("Mexican"));
        dataset.add(new PreferenceCard("Chinese"));
        dataset.add(new PreferenceCard("Japanese"));
        dataset.add(new PreferenceCard("Thai"));
        dataset.add(new PreferenceCard("Spanish"));
        dataset.add(new PreferenceCard("Indian"));
        dataset.add(new PreferenceCard("Greek"));
        dataset.add(new PreferenceCard("French"));
    }
}