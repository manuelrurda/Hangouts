package com.example.hangouts;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hangouts.models.Hangout;
import com.example.hangouts.models.User;
import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class HomeViewModel extends ViewModel {

    public static final String TAG = "HomeViewModel";

    public MutableLiveData<List<Hangout>> activeHangouts = new MutableLiveData<>();
    public MutableLiveData<List<Hangout>> pastHangouts = new MutableLiveData<>();
    private ParseUser currentUser;
    private Hangout hangout;

    public void init(){
        currentUser = ParseUser.getCurrentUser();
    }

    public void getActiveHangouts(){
        List<Hangout> hangouts = currentUser.getList(User.KEY_ACTIVEHANGOUTS);
        activeHangouts.setValue(hangouts);
    }

    public void getPastHangouts(){
        List<Hangout> hangouts = currentUser.getList(User.KEY_PASTHANGOUTS);
        pastHangouts.setValue(hangouts);
    }


    public void closeHangout() {
        updateHangout();
        updateUser();
    }

    private void updateHangout() {
        ParseQuery<Hangout> query = ParseQuery.getQuery(Hangout.class);
        query.getInBackground(hangout.getObjectId(), new GetCallback<Hangout>() {
            @Override
            public void done(Hangout fetchedHangout, ParseException e) {
                fetchedHangout.put(Hangout.KEY_ISACTIVE, true);
                fetchedHangout.saveInBackground();
            }
        });
    }

    private void updateUser() {
        JSONArray activeHangouts = currentUser.getJSONArray(User.KEY_ACTIVEHANGOUTS);
        for (int i = 0; i < activeHangouts.length(); i++) {
            try {
                JSONObject hangoutJSONObject = activeHangouts.getJSONObject(i);
                if (hangoutJSONObject.getString(Hangout.KEY_OBJECT_ID).equals(hangout.getObjectId())){
                    activeHangouts.remove(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JSONArray pastHangouts = currentUser.getJSONArray(User.KEY_PASTHANGOUTS);
        pastHangouts.put(hangout);
        currentUser.put(User.KEY_PASTHANGOUTS, pastHangouts);
        currentUser.put(User.KEY_ACTIVEHANGOUTS, activeHangouts);
        currentUser.saveInBackground();
    }

    public void setHangout(Hangout fetchedHangout) {
        this.hangout = fetchedHangout;
    }
}
