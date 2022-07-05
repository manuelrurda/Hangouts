package com.example.hangouts.models;

import com.parse.ParseClassName;
import com.parse.ParseUser;

@ParseClassName("User")
public class User extends ParseUser {
    public static final String KEY_NAME = "name";
    public static final String KEY_LASTNAME = "lastName";
    public static final String KEY_CUSINEPREFERENCE = "cuisinePreference";
    public static final String KEY_ONBOARDINGCOMPLETED = "onboardingCompleted";
}
