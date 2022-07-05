package com.example.hangouts.onboardingScreen.models;

public class PreferenceCard {

    private String value;

    public PreferenceCard(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "PreferenceCard{" +
                "value='" + value + '\'' +
                '}';
    }
}
