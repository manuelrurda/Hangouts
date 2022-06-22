package com.example.hangouts;

import android.app.Application;

import com.example.hangouts.models.User;
import com.parse.Parse;
import com.parse.ParseUser;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseUser.registerSubclass(User.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(BuildConfig.PARSE_APP_ID)
                .clientKey(BuildConfig.PARSE_CLIENT_KEY)
                .server(BuildConfig.PARSE_SERVER_URL)
                .build()
        );
    }
}
