package com.example.hangouts.homeScreen;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hangouts.R;
import com.example.hangouts.databinding.FragmentHangoutDetailsBinding;
import com.example.hangouts.homeScreen.utils.DateTimeUtil;
import com.example.hangouts.models.Hangout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;

public class HangoutDetailsFragment extends Fragment {

    private FragmentHangoutDetailsBinding binding;
    private Hangout hangout;

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

        setDateTimeTextViews();
        binding.tvHangoutDetailsAlias.setText(hangout.getAlias());
        binding.tvHangoutDetailsHangoutCode.setText(hangout.getObjectId());
        binding.btnCopyClipboard.setOnClickListener(this::onClipboardClick);
        binding.btnDetailsOk.setOnClickListener(this::onOkClick);
        binding.tvHangoutDetailsLocation.setText(hangout.getLocationString());
        initMapFragment();
    }

    private void onOkClick(View view) {

        FragmentManager fm = getParentFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }

        fm.beginTransaction()
                .replace(R.id.homeFragmentContainer, new HomeFragment())
                .addToBackStack("")
                .commit();
    }

    private void onClipboardClick(View view) {
        ClipboardManager clipboard =
                (ClipboardManager) getActivity()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Hangout Code", hangout.getObjectId());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    private void setDateTimeTextViews() {
        Date deadline = hangout.getDeadline();
        binding.tvHangoutDetailsDate.setText(DateTimeUtil.getDateString(deadline));
        binding.tvHangoutDetailsTime.setText(DateTimeUtil.getTimeString(deadline));
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