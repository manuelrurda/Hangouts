package com.example.hangouts.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hangouts.models.PreferenceCard;
import com.example.hangouts.repositories.CuisinePreferenceCardRepository;

import java.util.List;

public class DragDropCuisineViewModel extends ViewModel {

    public static final String TAG = "DragDropCuisineViewModel";

    private MutableLiveData<List<PreferenceCard>> cuisinePreferenceCards;
    private CuisinePreferenceCardRepository repo;

    public void init(){
        if(cuisinePreferenceCards != null){
            return;
        }
        repo = CuisinePreferenceCardRepository.getInstance();
        cuisinePreferenceCards = repo.getCuisineTypes();
    }

    public LiveData<List<PreferenceCard>> getCuisinePreferenceCards(){
        return cuisinePreferenceCards;
    }
}