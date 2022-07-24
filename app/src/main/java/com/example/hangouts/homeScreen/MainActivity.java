package com.example.hangouts.homeScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.example.hangouts.BayesianRating;
import com.example.hangouts.BuildConfig;
import com.example.hangouts.FrequencyMatrixGenerator;
import com.example.hangouts.R;
import com.google.android.libraries.places.api.Places;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Places.initialize(getApplicationContext(), BuildConfig.GOOGLE_CLOUD_API_KEY);

        List<Double> user1 = Arrays.asList(2.5,  3.0,  3.0,  3.0,  3.5);
        List<Double> user2 = Arrays.asList(0.0,  0.5,  2.0,  4.0,  5.0);
        List<Double> user3 = Arrays.asList(1.0,  3.0,  5.0,  5.5,  2.5);
        List<Double> user4 = Arrays.asList(2.0,  3.5,  4.0,  4.5,  0.0);

        List<List<Double>> preferenceMatrix = Arrays.asList(user1, user2, user3, user4);
        List<List<Double>> frequencyMatrix = FrequencyMatrixGenerator
                .fromPreferenceMatrix(preferenceMatrix);

        System.out.println(frequencyMatrix.size());
        for (int i = 0; i < frequencyMatrix.size(); i++) {
            System.out.println(BayesianRating.getBayesianRating(frequencyMatrix.get(i), 0.95));
        }
    }
}