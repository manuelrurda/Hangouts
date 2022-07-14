package com.example.hangouts.homeScreen.fragments;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.hangouts.BuildConfig;
import com.example.hangouts.models.Hangout;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import okhttp3.Headers;

public class CreateHangoutViewModel extends ViewModel {

    public static final String TAG = "VIEWMODEL";

    private final String REVERSE_GEOCODING_ENDPOINT="https://maps.googleapis.com/maps/api/geocode/json";
    private final AsyncHttpClient client = new AsyncHttpClient();


    MutableLiveData<LatLng> hangoutLocation = new MutableLiveData<>();
    MutableLiveData<String> hangoutLocationDecoded = new MutableLiveData<>();
    MutableLiveData<Date> hangoutDate = new MutableLiveData<>();
    MutableLiveData<Date> hangoutTime = new MutableLiveData<>();
    private String hangoutAlias = "";
    MutableLiveData<Hangout> newHangout = new MutableLiveData<>();
    MutableLiveData<Errors> errors = new MutableLiveData();

    public void setHangoutLocation(LatLng hangoutLocation) {
        this.hangoutLocation.setValue(hangoutLocation);
    }

    public LiveData<LatLng> getHangoutLocation() {
        return hangoutLocation;
    }

    private void setHangoutLocationDecoded(String hangoutLocationDecoded){
        this.hangoutLocationDecoded.setValue(hangoutLocationDecoded);
    }

    public void setHangoutDate(Date date){
        this.hangoutDate.setValue(date);
    }


    public void setHangoutTime(Date time) {
        this.hangoutTime.setValue(time);
    }

    public void setHangoutAlias(String hangoutAlias){
        this.hangoutAlias = hangoutAlias;
    }

    public void decodeHangoutLocation(LatLng hangoutLocation) {
        String decodedLocation = "";
        String latLngString = String.format("%s,%s", String.valueOf(hangoutLocation.latitude),
                String.valueOf(hangoutLocation.longitude));
        RequestParams params = new RequestParams();
        params.put("latlng", latLngString);
        params.put("result_type", "locality");
        params.put("key", BuildConfig.GOOGLE_CLOUD_API_KEY);
        client.get(REVERSE_GEOCODING_ENDPOINT, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONObject plusCode = json.jsonObject.getJSONObject("plus_code");
                    String compoundCode = plusCode.getString("compound_code");
                    String decodedLocation = compoundCode.substring(
                            compoundCode.indexOf(" ")+1, compoundCode.length()-1);
                    setHangoutLocationDecoded(decodedLocation);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                errors.postValue(Errors.ERROR_LOCATION_DECODING_FAILED);
            }
        });
    }

    public void onCreateClick() {
        String alias = hangoutAlias;
        Date date = hangoutDate.getValue();
        Date time = hangoutTime.getValue();

        if(alias.isEmpty() || date == null || time == null){
            errors.postValue(Errors.ERROR_ALL_FIELDS_REQUIRED);
        }else{
            createHangout();
        }
    }

    private void createHangout() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(ParseUser.getCurrentUser());
        Date deadline = getDeadline();

        LatLng latLngLocation = hangoutLocation.getValue();
        ParseGeoPoint location = new ParseGeoPoint(
                latLngLocation.latitude, latLngLocation.longitude);

        Hangout hangout = new Hangout();
        hangout.put(Hangout.KEY_ALIAS, hangoutAlias);
        hangout.put(Hangout.KEY_DEADLINE, deadline);
        hangout.put(Hangout.KEY_LOCATION, location);
        hangout.put(Hangout.KEY_LOCATION_STRING, hangoutLocationDecoded.getValue());
        hangout.put(Hangout.KEY_MEMBERS, jsonArray);

        hangout.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if(e != null){
                    errors.postValue(Errors.ERROR_SAVING_HANGOUT);
                    Log.e(TAG, "done: Error saving hangout on Parse", e);
                }else{
                    newHangout.postValue(hangout);
                    Log.d(TAG, "done: " + hangout.getObjectId());
                }
            }
        });
    }

    private Date getDeadline() {
        Calendar calendar = Calendar.getInstance();
        Date date = hangoutDate.getValue();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Date time = hangoutTime.getValue();
        calendar.setTime(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        calendar.set(year, month, day, hour, min);
        return calendar.getTime();
    }

    enum Errors {
        ERROR_LOCATION_DECODING_FAILED,
        ERROR_ALL_FIELDS_REQUIRED,
        ERROR_SAVING_HANGOUT,
    }
}
