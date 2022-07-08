package com.example.hangouts.homeScreen.fragments;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hangouts.BuildConfig;
import com.example.hangouts.R;
import com.example.hangouts.databinding.FragmentCreateMapBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.Map;

public class HangoutLocationSelectionMapFragment extends Fragment{

    private static final String TAG = "HangoutLocationSelectionMapFragment";
    private static final String US_COUNTRY_CODE = "US";

    private FragmentCreateMapBinding binding;
    private double currentLatitude;
    private double currentLongitude;
    private FusedLocationProviderClient fusedLocationClient;

    private OnMapReadyCallback onMapReadyCallback = (googleMap) -> {
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        LatLng currentLocation = new LatLng(currentLatitude, currentLongitude);
        googleMap.addMarker(new MarkerOptions().position(currentLocation).title("You"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10));
    };

    private final ActivityResultLauncher<String[]> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
                @Override
                public void onActivityResult(Map<String, Boolean> result) {
                    for (Map.Entry<String, Boolean> permissionResult: result.entrySet()) {
                        Log.d(TAG, "onActivityResult: " + permissionResult.getKey() + " VAL: " + permissionResult.getValue());
                    }
                    getLastLocation();
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateMapBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fusedLocationClient = LocationServices
                .getFusedLocationProviderClient(getActivity());
        initAutocompleteFragment();
    }

    private void initAutocompleteFragment() {
        Places.initialize(getContext(), BuildConfig.GOOGLE_CLOUD_API_KEY);
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocompleteFragment);
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);
        autocompleteFragment.setCountries(US_COUNTRY_CODE);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onError(@NonNull Status status) {
                Log.d(TAG, "onError: " + status.toString());
            }
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                final double latitude = place.getLatLng().latitude;
                final double longitude = place.getLatLng().longitude;
                Toast.makeText(getContext(), "Lat: " + latitude + " Long: " + longitude, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initMapFragment() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(onMapReadyCallback);
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (LocationUtils.isLocationEnabled(getContext())) {
            updateLocation();
            fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location == null) {
                        Log.d(TAG, "onComplete: Location is null");
                        Toast.makeText(getContext(), "Location Null", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Lat: " + String.valueOf(location.getLatitude())
                                + " Long: " + String.valueOf(location.getLongitude()),
                                Toast.LENGTH_SHORT).show();
                        currentLatitude = location.getLatitude();
                        currentLongitude = location.getLongitude();
                    }
                    initMapFragment();
                }
            });
        } else {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }



    @SuppressLint("MissingPermission")
    private void updateLocation() {
        LocationRequest locationRequest = LocationUtils.getLocationRequest();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }


    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location lastLocation = locationResult.getLastLocation();
            currentLatitude = lastLocation.getLatitude();
            currentLongitude = lastLocation.getLongitude();
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        if(LocationUtils.checkLocationPermissions(getContext())){
            getLastLocation();
        }else{
            requestPermissions();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

     private void requestPermissions() {
        requestPermissionLauncher.launch( new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION});
    }
}