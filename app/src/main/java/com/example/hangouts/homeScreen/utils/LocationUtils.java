package com.example.hangouts.homeScreen.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.Priority;

import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;

/***
 * Util class to get device current location
 */
public class LocationUtils {

    public static boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public static boolean checkLocationPermissions(Context context) {
        return ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public static LocationRequest getLocationRequest(){
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(50);
        locationRequest.setNumUpdates(1);
        return locationRequest;
    }
}
