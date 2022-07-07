package com.example.hangouts.homeScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.hangouts.BuildConfig;
import com.example.hangouts.R;
import com.google.android.libraries.places.api.Places;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Places.initialize(getApplicationContext(), BuildConfig.GOOGLE_CLOUD_API_KEY);
    }

    // Temporary logout
    public void onClickLogout(View view){
        ParseUser.logOutInBackground();
    }
}