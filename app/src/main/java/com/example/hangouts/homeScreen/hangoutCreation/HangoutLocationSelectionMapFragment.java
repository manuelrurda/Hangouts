package com.example.hangouts.homeScreen.hangoutCreation;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.Button;

import com.example.hangouts.BuildConfig;
import com.example.hangouts.R;
import com.example.hangouts.databinding.FragmentHangoutLocationSelectionMapBinding;
import com.example.hangouts.homeScreen.utils.LocationUtils;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.Map;

public class HangoutLocationSelectionMapFragment extends Fragment implements OnMapReadyCallback{

    private static final String TAG = "HangoutLocationSelectionMapFragment";
    private static final String US_COUNTRY_CODE = "US";

    private FragmentHangoutLocationSelectionMapBinding binding;
    private CreateHangoutViewModel viewModel;

    private FusedLocationProviderClient fusedLocationClient;

    private GoogleMap map;
    private Marker marker;

    private final ActivityResultLauncher<String[]> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(), this::handlePermissionResponse);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices
                .getFusedLocationProviderClient(getActivity());
        viewModel = new ViewModelProvider(requireActivity()).get(CreateHangoutViewModel.class);
        if(LocationUtils.checkLocationPermissions(getContext())){
            getCurrentLocation();
        }else{
            requestPermissions();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHangoutLocationSelectionMapBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnMapFragmentNext.setOnClickListener(this::onClickNext);
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
                LatLng location = new LatLng(latitude, longitude);
                viewModel.setHangoutLocation(location);
            }
        });
    }

    private void initMapFragment() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void initMarker(LatLng location) {
        marker = map.addMarker(new MarkerOptions().position(location));
        map.moveCamera(CameraUpdateFactory.newLatLng(location));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        if (LocationUtils.isLocationEnabled(getContext())) {
            fusedLocationClient.getLastLocation()
                    .addOnCompleteListener(location -> {
                        if(location.getResult() != null){
                            LatLng latLngLocation = new LatLng(location.getResult().getLatitude(),
                                    location.getResult().getLongitude());
                            viewModel.setHangoutLocation(latLngLocation);
                        }else{
                            updateLocation();
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
    private void updateLocation() {
        LocationRequest locationRequest = LocationUtils.getLocationRequest();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location lastLocation = locationResult.getLastLocation();
            LatLng location = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            viewModel.setHangoutLocation(location);
        }
    };

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);

        // TODO: hanlde if clicked in ocean
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng location) {
                viewModel.setHangoutLocation(location);
            }
        });

        viewModel.hangoutLocation.observe(requireActivity(), this::setMarkerPosition);
    }

    private void handlePermissionResponse(Map<String, Boolean> result){
        for (Map.Entry<String, Boolean> permissionResult: result.entrySet()) {
            Log.d(TAG, "handlePermissionResponse: " + permissionResult.getValue());
            if(permissionResult.getValue()){
                getCurrentLocation();
                initMapFragment();
                return;
            }
        }
        Log.d(TAG, "handlePermissionResponse: Permission Denied");
    }

    private void requestPermissions() {
        requestPermissionLauncher.launch( new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION});
    }

    private void setMarkerPosition(LatLng location){
        if(location != null){
            if(marker == null){
                initMarker(location);
            }
            marker.setPosition(location);
            map.animateCamera(CameraUpdateFactory.newLatLng(location));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
        }
    }

    private void onClickNext(View view) {
        viewModel.decodeHangoutLocation(viewModel.getHangoutLocation().getValue());
        getParentFragmentManager().beginTransaction()
                .replace(R.id.homeFragmentContainer, new CreateHangoutFragment())
                .addToBackStack("")
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(LocationUtils.checkLocationPermissions(getContext())){
            initMapFragment();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}