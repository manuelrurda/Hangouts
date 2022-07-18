package com.example.hangouts;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hangouts.models.Hangout;
import com.example.hangouts.models.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.List;

public class HomeViewModel extends ViewModel {

    public static final String TAG = "HomeViewModel";

    MutableLiveData<JSONArray> activeHangouts = new MutableLiveData<>();

    public void getActiveHangouts(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(User.KEY_ACTIVEHANGOUTS);
        query.whereEqualTo(User.KEY_OBJECT_ID, ParseUser.getCurrentUser().getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Error fetching active hangouts: ", e);
                }
                Log.d(TAG, "done: " + objects.toString());
            }
        });
    }
}
