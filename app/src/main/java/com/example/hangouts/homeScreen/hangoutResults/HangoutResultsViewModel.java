package com.example.hangouts.homeScreen.hangoutResults;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.hangouts.BayesianRating;
import com.example.hangouts.BuildConfig;
import com.example.hangouts.models.Hangout;
import com.example.hangouts.models.User;
import com.example.hangouts.onboardingScreen.CuisinePreferenceRepository;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;

public class HangoutResultsViewModel extends ViewModel {

    public static final String TAG = "ResultsViewModel";
    private final int MAX_RATING = 5;

    private Hangout hangout;
    private HashMap<Double, String> cuisineRatingMap = new HashMap<>();
    public MutableLiveData<HashMap<String, List<Double>>> frequencyMap = new MutableLiveData<>();
    public MutableLiveData<List<Double>> scoreList = new MutableLiveData<>();

    public HashMap<Double, String> getCuisineRatingMap() {
        return cuisineRatingMap;
    }

    public void setHangout(Hangout hangout) {
        this.hangout = hangout;
    }

    public Hangout getHangout(){
        return this.hangout;
    }

    public void generateFrequencyMatrix(Hangout hangout) {
        ParseQuery<Hangout> query = ParseQuery.getQuery(Hangout.class);
        query.getInBackground(hangout.getObjectId(), new GetCallback<Hangout>() {
            @Override
            public void done(Hangout fetchedHangout, ParseException e) {
                JSONArray members = fetchedHangout.getMembers();
                HashMap<String, List<Double>> newFrequencyMap = getZeroMap();
                for (int i = 0; i < members.length(); i++) {
                    try {
                        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
                        ParseUser member = userQuery.get(members.getJSONObject(i).getString(User.KEY_OBJECT_ID));
                        JSONObject cuisinePreferences = member.getJSONObject(User.KEY_CUSINEPREFERENCES);
                        for (String cuisine : CuisinePreferenceRepository.CUISINE_TYPES) {
                            double userRating = 0;
                            try {
                                userRating = cuisinePreferences.getDouble(cuisine);
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                            // will cast to int for now, double will come into play when weighting
                            double currentRating = newFrequencyMap.get(cuisine).get((int) userRating);
                            newFrequencyMap.get(cuisine).set((int) userRating, currentRating + 1);
                        }
                    } catch (JSONException | ParseException ex) {
                        ex.printStackTrace();
                    }
                }
                Log.d(TAG, "Final: " + newFrequencyMap.toString());
                frequencyMap.setValue(newFrequencyMap);
            }
        });
    }

    private HashMap<String, List<Double>> getZeroMap() {
        HashMap<String, List<Double>> zeroMap = new HashMap<>();
        for (String cuisine : CuisinePreferenceRepository.CUISINE_TYPES) {
            Double[] zeroArray = new Double[MAX_RATING + 1];
            Arrays.fill(zeroArray, 0.0);
            zeroMap.put(cuisine, Arrays.asList(zeroArray));
        }
        return zeroMap;
    }

    public void getScores(HashMap<String, List<Double>> frequencyMap) {
        List<Double> scores = new ArrayList<>();
        for (Map.Entry<String, List<Double>> set : frequencyMap.entrySet()) {
            double score = BayesianRating.getBayesianRating(set.getValue(), 0.95);
            cuisineRatingMap.put(score, set.getKey());
            scores.add(score);
        }
        Collections.sort(scores, Collections.reverseOrder());
        scoreList.postValue(scores);
    }
}
