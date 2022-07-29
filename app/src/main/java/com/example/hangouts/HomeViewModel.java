package com.example.hangouts;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hangouts.models.Hangout;
import com.example.hangouts.models.User;
import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;


import java.util.List;

public class HomeViewModel extends ViewModel {

    public static final String TAG = "HomeViewModel";

    public MutableLiveData<List<Hangout>> activeHangouts = new MutableLiveData<>();
    private Hangout hangout;

    public void getActiveHangouts(){
        ParseUser currentUser = ParseUser.getCurrentUser();
        List<Hangout> hangouts = currentUser.getList(User.KEY_ACTIVEHANGOUTS);
        activeHangouts.setValue(hangouts);
    }

    public void closeHangout() {
        ParseQuery<Hangout> query = ParseQuery.getQuery(Hangout.class);
        query.getInBackground(hangout.getObjectId(), new GetCallback<Hangout>() {
            @Override
            public void done(Hangout object, ParseException e) {
                hangout.put(Hangout.KEY_ISACTIVE, false);
                hangout.saveInBackground();
            }
        });
    }

    public void setHangout(Hangout fetchedHangout) {
        this.hangout = fetchedHangout;
    }
}
