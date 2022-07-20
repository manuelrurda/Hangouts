package com.example.hangouts.homeScreen.hangoutJoining;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hangouts.models.Hangout;
import com.example.hangouts.models.User;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JoinHangoutViewModel extends ViewModel {

    public static final String TAG = "JoinViewModel";

    MutableLiveData<Errors> errors = new MutableLiveData();
    MutableLiveData<Hangout> toJoinHangout = new MutableLiveData<>();

    public void joinHangout(Hangout hangout){
        if(!userInHanogut(hangout)){

        }else{
            errors.postValue(Errors.USER_ALREADY_IN_HANGOUT);
        }
    }

    private boolean userInHanogut(Hangout hangout) {
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        JSONArray members = hangout.getMembers();
        for(int i = 0; i < members.length(); i++){
            try {
                JSONObject member = members.getJSONObject(i);
                if(member.get(User.KEY_OBJECT_ID).equals(currentUserId)){
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void findHangout(String hangoutId){
        if(hangoutId.isEmpty()){
            errors.postValue(Errors.EMPTY_HANGOUTID_STRING);
        }else{
            ParseQuery<Hangout> query = ParseQuery.getQuery(Hangout.class);
            query.getInBackground(hangoutId, new GetCallback<Hangout>() {
                @Override
                public void done(Hangout hangout, ParseException e) {
                    if(e != null){
                        errors.postValue(Errors.HANGOUT_NOT_FOUND);
                    }else{
                        toJoinHangout.postValue(hangout);
                    }
                }
            });
        }
    }

    enum Errors {
        HANGOUT_NOT_FOUND,
        EMPTY_HANGOUTID_STRING,
        USER_ALREADY_IN_HANGOUT
    }
}
