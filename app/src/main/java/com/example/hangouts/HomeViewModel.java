package com.example.hangouts;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hangouts.models.Hangout;
import com.example.hangouts.models.User;
import com.parse.ParseUser;


import java.util.List;

public class HomeViewModel extends ViewModel {

    public static final String TAG = "HomeViewModel";

    public MutableLiveData<List<Hangout>> activeHangouts = new MutableLiveData<>();

    public void getActiveHangouts(){
        ParseUser currentUser = ParseUser.getCurrentUser();
        List<Hangout> hangouts = currentUser.getList(User.KEY_ACTIVEHANGOUTS);
        activeHangouts.setValue(hangouts);
    }
}
