package com.example.hangouts.homeScreen.hangoutResults;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.hangouts.BuildConfig;
import com.example.hangouts.models.Hangout;
import com.parse.ParseGeoPoint;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

import okhttp3.Headers;

public class HangoutResultsMapViewModel extends ViewModel {

    public static final String GOOGLE_PLACES_SEARCH_ENDPOINT = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
    private final AsyncHttpClient client = new AsyncHttpClient();

    public MutableLiveData<JSONArray> recommendationResults = new MutableLiveData<>();
    public MutableLiveData<List<Double>> scoreList = new MutableLiveData<>();
    private HashMap<Double, String> cuisineRatingMap = new HashMap<>();
    private Hangout hangout;

    public HashMap<Double, String> getCuisineRatingMap() {
        return cuisineRatingMap;
    }

    public void init(List<Double> scoreList, HashMap<Double, String> cuisineRatingMap, Hangout hangout){
        this.scoreList.setValue(scoreList);
        this.cuisineRatingMap = cuisineRatingMap;
        this.hangout = hangout;
    }

    public void queryReccomendations() {
        HashMap<Double, String> cuisineRatingMap = getCuisineRatingMap();
        List<Double> scoreList = this.scoreList.getValue();
        String firstPlace = cuisineRatingMap.get(scoreList.get(0));
        ParseGeoPoint parseGeoPoint = hangout.getLocation();
        String latSting = String.valueOf(parseGeoPoint.getLatitude());
        String lngSting = String.valueOf(parseGeoPoint.getLongitude());
        String url = String.format("%s?location=%s%%2C%s&radius=25000&type=restaurant&keyword=%s&key=%s",
                GOOGLE_PLACES_SEARCH_ENDPOINT,
                latSting,
                lngSting,
                firstPlace,
                BuildConfig.GOOGLE_CLOUD_API_KEY);
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    recommendationResults.postValue(results);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });

    }

}
