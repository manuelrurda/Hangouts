package com.example.hangouts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FrequencyMatrixGenerator {

    public static List<List<Double>> fromPreferenceMatrix(List<List<Double>> preferenceMatrix){
        int categoryAmount = preferenceMatrix.get(0).size();
        int memberAmount = preferenceMatrix.size();
        List<List<Double>> frequencyMatrix = new ArrayList<>();
        for (int i = 0; i < categoryAmount; i++){
            Double[] emptyFrequencyArray = new Double[(categoryAmount + 1) * 2];
            Arrays.fill(emptyFrequencyArray, 0.0);
            frequencyMatrix.add(Arrays.asList(emptyFrequencyArray));
            for (int j = 0; j < memberAmount; j++){
                int ratingIndex = (int) (preferenceMatrix.get(j).get(i) * 2);
                List<Double> frequencyArray = frequencyMatrix.get(i);
                double currentRating = frequencyArray.get(ratingIndex);
                frequencyArray.set(ratingIndex, currentRating + 1);
            }
        }
        return frequencyMatrix;
    }
}
