package com.example.hangouts.homeScreen.hangoutResults;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hangouts.models.Hangout;
import com.example.hangouts.models.User;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HangoutResultsViewModel extends ViewModel {

    public MutableLiveData<List<List<Double>>> preferenceMatrix = new MutableLiveData<>();

    public void generateFrequencyMatrix(Hangout hangout) {
        List<List<Double>> frequencyMatrix = new ArrayList<>();
        ParseQuery<Hangout> query = ParseQuery.getQuery(Hangout.class);
        query.getInBackground(hangout.getObjectId(), new GetCallback<Hangout>() {
            @Override
            public void done(Hangout fetchedHangout, ParseException e) {
                JSONArray members = fetchedHangout.getMembers();
                for (int i = 0; i < members.length(); i++) {
                    try {
                        JSONObject member = members.getJSONObject(i);
                        JSONArray preferenceArray = member.getJSONArray(User.KEY_CUSINEPREFERENCE);
                        for (int j = 0; j < preferenceArray.length(); j++) {
                            JSONArray jRating = preferenceArray.getJSONArray(i);

                        }

                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
    }
}
