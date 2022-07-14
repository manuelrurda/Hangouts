package com.example.hangouts.models;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import org.json.JSONArray;

import java.util.Date;

@ParseClassName("Hangout")
public class Hangout extends ParseObject {
    public static final String FRAGMENT_ARGUMENT_ID = "hangout";
    public static final String KEY_ALIAS = "alias";
    public static final String KEY_DEADLINE = "deadline";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_LOCATION_STRING = "locationString";
    public static final String KEY_MEMBERS = "members";
    public static final String KEY_ISACTIVE = "isActive";

    public String getAlias(){
        return getString(KEY_ALIAS);
    }

    public Date getDeadline(){
        return getDate(KEY_DEADLINE);
    }

    public ParseGeoPoint getLocation(){
        return getParseGeoPoint(KEY_LOCATION);
    }

    public String getLocationString(){
        return getString(KEY_LOCATION_STRING);
    }

    public JSONArray getMembers(){
        return getJSONArray(KEY_MEMBERS);
    }

    public Boolean getIsActive(){
        return getBoolean(KEY_ISACTIVE);
    }

}
