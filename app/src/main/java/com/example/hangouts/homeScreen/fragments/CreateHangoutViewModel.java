package com.example.hangouts.homeScreen.fragments;

import android.app.Notification;
import android.util.Log;
import android.widget.Toast;

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
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                actions.postValue(Actions.ERROR_LOCATION_DECODING_FAILED);
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
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(ParseUser.getCurrentUser());
        Hangout hangout = new Hangout();
        hangout.put(Hangout.KEY_ALIAS, hangoutAlias);
        hangout.put(Hangout.KEY_DATE, hangoutDate.getValue());
        hangout.put(Hangout.KEY_TIME, hangoutTime.getValue());
        hangout.put(Hangout.KEY_MEMBERS, jsonArray);

        hangout.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    actions.postValue(Actions.ERROR_SAVING_HANGOUT);
                    Log.e(TAG, "done: Error saving hangout on Parse", e);
                }else{
                    actions.postValue(Actions.SUCCESS_SAVING_HANGOUT);
                    Log.d(TAG, "done: " + hangout.getObjectId());
                }
            }
        });
    }

    enum Actions {
        ERROR_LOCATION_DECODING_FAILED,
        ERROR_ALL_FIELDS_REQUIRED,
        ERROR_SAVING_HANGOUT,
        SUCCESS_SAVING_HANGOUT
    }
}
