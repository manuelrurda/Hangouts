package com.example.hangouts.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Hangout")
public class Hangout extends ParseObject {
    public static final String KEY_ALIAS = "alias";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    public static final String KEY_MEMBERS = "members";
    public static final String KEY_ISACTIVE = "isActive";
}
