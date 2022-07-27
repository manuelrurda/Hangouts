package com.example.hangouts.homeScreen.hangoutResults;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
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
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseGeoPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMapFragment();
        viewModel = new ViewModelProvider(requireActivity()).get(HangoutResultsViewModel.class);
        viewModel.recommendationResults.observe(getViewLifecycleOwner(), this::onRecommendationsQueried);
    }

    @SuppressLint("ResourceType")
    private void onRecommendationsQueried(JSONArray results) {
        for (int i = 0; i < results.length(); i++) {
            try {
                JSONObject restaurant = results.getJSONObject(i);
                String name = restaurant.getString("name");
                JSONObject location = restaurant.getJSONObject("geometry").getJSONObject("location");
                double latitude = location.getDouble("lat");
                double longitude = location.getDouble("lng");
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title(name)
                        .icon(getMarkerIcon(getContext().getResources().getString(
                                R.color.gold
                        ))));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
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
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        ParseGeoPoint hangoutLocation = viewModel.getHangout().getLocation();
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                hangoutLocation.getLatitude(),
                hangoutLocation.getLongitude()), 10));
        populateMap();
    }
}