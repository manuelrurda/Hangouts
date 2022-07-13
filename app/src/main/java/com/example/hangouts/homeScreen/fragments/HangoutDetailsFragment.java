package com.example.hangouts.homeScreen.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hangouts.R;
import com.example.hangouts.databinding.FragmentHangoutDetailsBinding;
import com.example.hangouts.models.Hangout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HangoutDetailsFragment extends Fragment {

    private FragmentHangoutDetailsBinding binding;
    private Hangout hangout;

    private TextView tvHangoutDetailsAlias;
    private TextView tvHangoutDetailsLocation;
    private TextView tvHangoutDetailsDate;
    private TextView tvHangoutDetailsTime;
    private TextView tvHangoutDetailsHangoutCode;

    public HangoutDetailsFragment() {
    }

    public static HangoutDetailsFragment newInstance(Hangout hangout) {
        HangoutDetailsFragment fragment = new HangoutDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(Hangout.FRAGMENT_ARGUMENT_ID, hangout);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getArguments().isEmpty()) {
            hangout = getArguments().getParcelable(Hangout.FRAGMENT_ARGUMENT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHangoutDetailsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHangoutDetailsAlias = binding.tvHangoutDetailsAlias;
        tvHangoutDetailsAlias.setText(hangout.getAlias());

        tvHangoutDetailsLocation = binding.tvHangoutDetailsLocation;
        tvHangoutDetailsLocation.setText(hangout.getLocationString());

        tvHangoutDetailsDate = binding.tvHangoutDetailsDate;
        tvHangoutDetailsTime = binding.tvHangoutDetailsTime;
        setDateTimeTextViews();

        tvHangoutDetailsHangoutCode = binding.tvHangoutDetailsHangoutCode;
        tvHangoutDetailsHangoutCode.setText(hangout.getObjectId());

        initMapFragment();
    }

    private void setDateTimeTextViews() {
        Date deadline = hangout.getDeadline();
        String dateFormat = "MM/dd/yy";
        String timeFormat = "HH:mm";
        SimpleDateFormat dateSimpleFormat = new SimpleDateFormat(dateFormat, Locale.US);
        SimpleDateFormat timeSimpleformat = new SimpleDateFormat(timeFormat, Locale.US);
        tvHangoutDetailsDate.setText(dateSimpleFormat.format(deadline));
        tvHangoutDetailsTime.setText(timeSimpleformat.format(deadline));
    }

    private void initMapFragment() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager()
                        .findFragmentById(R.id.detailsMapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(onMapReadyCallback);
        }
    }

    OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            LatLng location = new LatLng(hangout.getLocation().getLatitude(),
                    hangout.getLocation().getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(hangout.getAlias()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
        }
    };
}