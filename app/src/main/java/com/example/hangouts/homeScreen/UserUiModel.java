package com.example.hangouts.homeScreen;

import com.example.hangouts.models.User;
import com.parse.ParseUser;

/***
 * UserUiModel - model for getting User UI data
 */
public class UserUiModel {

    ParseUser parseUser;

    public UserUiModel(ParseUser parseUser){
        this.parseUser = parseUser;
    }

    public String getName(){
        return parseUser.getString(User.KEY_NAME);
    }

    public String getLastName(){
        return parseUser.getString(User.KEY_LASTNAME);
    }

    public String getLastInitial(){
        if(getLastName().isEmpty()){
            return "";
        }
        return getLastName().substring(0, 1);
    }

}
