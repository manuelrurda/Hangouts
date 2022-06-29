package com.example.hangouts.repositories;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

// Singleton
public class CuisineTypeRepository {

    private static  CuisineTypeRepository instance;
    private ArrayList<String> dataset = new ArrayList<>();

    public static CuisineTypeRepository getInstance(){
        if (instance == null){
            instance = new CuisineTypeRepository();
        }
        return instance;
    }

    public MutableLiveData<List<String>> getCuisineTypes(){
        setCuisineTypes();
        MutableLiveData<List<String>> data = new MutableLiveData<>();
        data.setValue(dataset);

        return data;
    }

    private void setCuisineTypes(){
        dataset.add("Italian");
        dataset.add("Mexican");
        dataset.add("Chinese");
        dataset.add("Japanese");
        dataset.add("Thai");
        dataset.add("American");
        dataset.add("Indian");
        dataset.add("Spanish");
    }
}