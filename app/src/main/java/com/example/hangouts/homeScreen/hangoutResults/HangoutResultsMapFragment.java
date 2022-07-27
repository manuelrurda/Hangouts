package com.example.hangouts.homeScreen.hangoutResults;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hangouts.R;
import com.example.hangouts.databinding.FragmentHangoutResultsMapBinding;
import com.example.hangouts.homeScreen.hangoutCreation.CreateHangoutViewModel;
import com.example.hangouts.homeScreen.utils.LocationUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

public class HangoutResultsMapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentHangoutResultsMapBinding binding;
    private HangoutResultsViewModel viewModel;
    private FusedLocationProviderClient fusedLocationClient;

    private GoogleMap map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHangoutResultsMapBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices
                .getFusedLocationProviderClient(getActivity());
        viewModel = new ViewModelProvider(requireActivity()).get(HangoutResultsViewModel.class);
        getCurrentLocation();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMapFragment();
        populateMap();
    }

    private void populateMap() {

        viewModel.queryReccomendations();
    }

    private void initMapFragment() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.resultsMapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        if (LocationUtils.isLocationEnabled(getContext())) {
            fusedLocationClient.getLastLocation()
                    .addOnCompleteListener(location -> {
                        if(location.getResult() != null){
                            LatLng latLngLocation = new LatLng(location.getResult().getLatitude(),
                                    location.getResult().getLongitude());
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    latLngLocation, 10));
                        }
                    })
                    .addOnFailureListener(e -> {
                        e.printStackTrace();
                    });
        } else {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0, 0), 10));
    }
}