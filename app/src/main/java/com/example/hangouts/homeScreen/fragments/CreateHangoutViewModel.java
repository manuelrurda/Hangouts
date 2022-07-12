package com.example.hangouts.homeScreen.fragments;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.hangouts.BuildConfig;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpCookie;
import java.util.Date;

import okhttp3.Headers;

public class CreateHangoutViewModel extends ViewModel {

    public static final String TAG = "VIEWMODEL";

    private final String REVERSE_GEOCODING_ENDPOINT="https://maps.googleapis.com/maps/api/geocode/json";
    private final AsyncHttpClient client = new AsyncHttpClient();


    public MutableLiveData<LatLng> hangoutLocation = new MutableLiveData<>();
    public MutableLiveData<String> hangoutLocationDecoded = new MutableLiveData<>();
    public MutableLiveData<Date> hangoutDate = new MutableLiveData<>();
    public MutableLiveData<Date> hangoutTime = new MutableLiveData<>();
    private String hangoutAlias = "";

    MutableLiveData<Actions> actions = new MutableLiveData();

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
                            compoundCode.indexOf(" "), compoundCode.length()-1);
                    setHangoutLocationDecoded(decodedLocation);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });
    }

    public void onCreateClick() {
        String alias = hangoutAlias;
        Date date = hangoutDate.getValue();
        Date time = hangoutTime.getValue();

        if(alias.isEmpty() || date == null || time == null){
            actions.postValue(Actions.ERROR_ALL_FIELDS_REQUIRED);
        }else{
            createHangout();
        }
    }

    private void createHangout() {

    }

    enum Actions {
        ERROR_ALL_FIELDS_REQUIRED,
        ERROR_SAVING_HANGOUT,
        SUCCESS
    }
}
