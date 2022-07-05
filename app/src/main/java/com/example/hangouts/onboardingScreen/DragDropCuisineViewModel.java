package com.example.hangouts.onboardingScreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hangouts.onboardingScreen.models.DropZone;
import com.example.hangouts.onboardingScreen.models.PreferenceCard;

import java.util.List;

public class DragDropCuisineViewModel extends ViewModel {

    public static final String TAG = "DragDropCuisineViewModel";


    private MutableLiveData<List<DropZone>> dropZones;
    private MutableLiveData<List<PreferenceCard>> cuisinePreferenceCards;
    private CuisinePreferenceCardRepository repo;

    public void init(){
        if(cuisinePreferenceCards != null){
            return;
        }
        repo = CuisinePreferenceCardRepository.getInstance();
        cuisinePreferenceCards = repo.getPreferenceCards();
    }

    public LiveData<List<PreferenceCard>> getCuisinePreferenceCards(){
        return cuisinePreferenceCards;
    }

    public void removeFirstPreferenceCard(){
        List<PreferenceCard> currentPreferenceCards = cuisinePreferenceCards.getValue();
        currentPreferenceCards.remove(0);
        cuisinePreferenceCards.postValue(currentPreferenceCards);
    }

}