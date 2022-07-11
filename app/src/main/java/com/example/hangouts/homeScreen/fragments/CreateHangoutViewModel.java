package com.example.hangouts.homeScreen.fragments;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

public class CreateHangoutViewModel extends ViewModel {

    public LatLng hangoutLocation;

    public void setHangoutLocation(LatLng hangoutLocation) {
        this.hangoutLocation = hangoutLocation;
    }

    public LatLng getHangoutLocation() {
        return hangoutLocation;
    }
}
