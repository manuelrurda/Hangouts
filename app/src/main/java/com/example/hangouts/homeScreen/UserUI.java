package com.example.hangouts.homeScreen;

import com.example.hangouts.models.User;
import com.parse.ParseUser;

public class UserUI {

    ParseUser parseUser;

    public UserUI (ParseUser parseUser){
        this.parseUser = parseUser;
    }

    public String getName(){
        return parseUser.getString(User.KEY_NAME);
    }

    public String getLastName(){
        return parseUser.getString(User.KEY_LASTNAME);
    }

    public String getLastInitial(){
        return getLastName().substring(0, 1);
    }
}
