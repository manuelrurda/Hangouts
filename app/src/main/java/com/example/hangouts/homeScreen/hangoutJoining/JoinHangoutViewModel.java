package com.example.hangouts.homeScreen.hangoutJoining;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hangouts.models.Hangout;
import com.example.hangouts.models.User;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JoinHangoutViewModel extends ViewModel {

    public static final String TAG = "JoinViewModel";

    MutableLiveData<Errors> errors = new MutableLiveData();
    MutableLiveData<Hangout> toJoinHangout = new MutableLiveData<>();
    MutableLiveData<Hangout> joinedHangout = new MutableLiveData<>();

    public void joinHangout(Hangout hangout) {
        if (!userInHanogut(hangout)) {
            JSONArray updatedMembers = hangout.getMembers();
            updatedMembers.put(ParseUser.getCurrentUser());
            hangout.put(Hangout.KEY_MEMBERS, updatedMembers);
            hangout.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        errors.postValue(Errors.ERROR_UPDATING_HANGOUT);
                    } else {
                        addHangoutToUser(hangout);
                    }
                }
            });
        } else {
            errors.postValue(Errors.ERROR_USER_ALREADY_IN_HANGOUT);
        }
    }

    private void addHangoutToUser(Hangout hangout) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        JSONArray updatedActiveHangouts = currentUser.getJSONArray(User.KEY_ACTIVEHANGOUTS);
        updatedActiveHangouts.put(hangout);
        currentUser.put(User.KEY_ACTIVEHANGOUTS, updatedActiveHangouts);
        currentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    errors.postValue(Errors.ERROR_ADDING_HANGOUT_TO_USER);
                }else{
                    joinedHangout.postValue(hangout);
                }
            }
        });
    }

    private boolean userInHanogut(Hangout hangout) {
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        JSONArray members = hangout.getMembers();
        for (int i = 0; i < members.length(); i++) {
            try {
                JSONObject member = members.getJSONObject(i);
                if (member.get(User.KEY_OBJECT_ID).equals(currentUserId)) {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void findHangout(String hangoutId) {
        if (hangoutId.isEmpty()) {
            errors.postValue(Errors.ERROR_EMPTY_HANGOUTID_STRING);
        } else {
            ParseQuery<Hangout> query = ParseQuery.getQuery(Hangout.class);
            query.getInBackground(hangoutId, new GetCallback<Hangout>() {
                @Override
                public void done(Hangout hangout, ParseException e) {
                    if (e != null) {
                        errors.postValue(Errors.ERROR_HANGOUT_NOT_FOUND);
                    } else {
                        toJoinHangout.postValue(hangout);
                    }
                }
            });
        }
    }

    enum Errors {
        ERROR_HANGOUT_NOT_FOUND,
        ERROR_EMPTY_HANGOUTID_STRING,
        ERROR_USER_ALREADY_IN_HANGOUT,
        ERROR_UPDATING_HANGOUT,
        ERROR_ADDING_HANGOUT_TO_USER
    }
}
