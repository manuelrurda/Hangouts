package com.example.hangouts.homeScreen.hangoutResults;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hangouts.R;
import com.example.hangouts.databinding.FragmentHangoutResultsMapBinding;
import com.example.hangouts.homeScreen.HangoutDetailsFragment;
import com.example.hangouts.homeScreen.HomeFragment;
import com.example.hangouts.homeScreen.hangoutCreation.CreateHangoutViewModel;
import com.example.hangouts.homeScreen.utils.LocationUtils;
import com.example.hangouts.models.Hangout;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HangoutResultsMapFragment extends Fragment implements OnMapReadyCallback {

    public static final String SCORE_LIST_ID = "score_list";
    public static final String CUISINE_RATING_MAP = "cuisine_rating_map";

    private FragmentHangoutResultsMapBinding binding;
    private HangoutResultsMapViewModel viewModel;
    private Hangout hangout;
    private List<Double> scoreList;
    private HashMap<Double, String> cuisineRatingMap;

    private GoogleMap map;

    public static HangoutResultsMapFragment newInstance(Hangout hangout, List<Double> scoreList,
                                                        HashMap<Double, String> cuisineRatingMap){
        HangoutResultsMapFragment fragment = new HangoutResultsMapFragment();
        Bundle args = new Bundle();
        args.putParcelable(Hangout.FRAGMENT_ARGUMENT_ID, hangout);
        ArrayList<Double> scoreArrayList = new ArrayList<>(scoreList);
        args.putSerializable(SCORE_LIST_ID, scoreArrayList);
        args.putSerializable(CUISINE_RATING_MAP, cuisineRatingMap);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getArguments().isEmpty()) {
            hangout = getArguments().getParcelable(Hangout.FRAGMENT_ARGUMENT_ID);
            scoreList = (List<Double>) getArguments().getSerializable(SCORE_LIST_ID);
            cuisineRatingMap = (HashMap<Double, String>) getArguments().getSerializable(CUISINE_RATING_MAP);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHangoutResultsMapBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMapFragment();
        viewModel = new ViewModelProvider(requireActivity()).get(HangoutResultsMapViewModel.class);
        viewModel.init(scoreList, cuisineRatingMap, hangout);
        viewModel.recommendationResults.observe(requireActivity(), this::onRecommendationsQueried);
        binding.btnResultsOk.setOnClickListener(this::onClickOK);
    }

    private void onClickOK(View view) {
        FragmentManager fm = getParentFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount()-1; ++i) {
            fm.popBackStack();
        }
        fm.beginTransaction()
                .replace(R.id.homeFragmentContainer, new HomeFragment())
                .commit();
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
        ParseGeoPoint hangoutLocation = hangout.getLocation();
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                hangoutLocation.getLatitude(),
                hangoutLocation.getLongitude()), 10));
        populateMap();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}