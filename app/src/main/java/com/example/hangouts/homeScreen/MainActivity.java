package com.example.hangouts.homeScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.example.hangouts.BayesianRating;
import com.example.hangouts.BuildConfig;
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

    }
}