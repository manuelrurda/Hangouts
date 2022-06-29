package com.example.hangouts.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hangouts.repositories.CuisineTypeRepository;

import java.util.List;

public class DragDropCuisineViewModel extends ViewModel {

    public static final String TAG = "DragDropCuisineViewModel";

    private MutableLiveData<List<String>> cuisineTypes;
    private CuisineTypeRepository repo;

    public void init(){
        if(cuisineTypes != null){
            return;
        }
        repo = CuisineTypeRepository.getInstance();
        cuisineTypes = repo.getCuisineTypes();
    }

    public LiveData<List<String>> getCuisineTypes(){
        return cuisineTypes;
    }
}