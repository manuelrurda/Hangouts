package com.example.hangouts.models;

import com.parse.ParseClassName;
import com.parse.ParseUser;

@ParseClassName("User")
public class User extends ParseUser {
    public static final String KEY_NAME = "name";
    public static final String KEY_LASTNAME = "lastName";
    public static final String KEY_CUSINEPREFERENCES = "cuisinePreferences";
    public static final String KEY_ONBOARDINGCOMPLETED = "onboardingCompleted";
    public static final String KEY_ACTIVEHANGOUTS = "activeHangouts";
    public static final String KEY_PASTHANGOUTS = "pastHangouts";
}
