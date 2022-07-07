package com.example.hangouts.loginScreen;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hangouts.models.User;

/**
 * ViewModel for SignupFragment
 */
public class SignupViewModel extends ViewModel {
    private static final String TAG = "SignupViewModel";

    MutableLiveData<Actions> actions = new MutableLiveData();

    public void onSignupClick(String name, String lastName, String username, String password) {
        if (name.isEmpty() || lastName.isEmpty() ||
                username.isEmpty() || password.isEmpty()) {
            actions.postValue(Actions.TOAST_ALL_FIELDS_REQUIRED);
        } else {
            signupUser(name, lastName, username, password);
        }
    }

    private void signupUser(String name, String lastName, String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.put(User.KEY_NAME, name);
        user.put(User.KEY_LASTNAME, lastName);

        user.signUpInBackground(e -> {
            if (e != null) {
                actions.postValue(Actions.TOAST_ERROR_SIGNING_UP);
                Log.e(TAG, "Error signing up user: ", e);
            } else {
                Log.d(TAG, "Signup Successful");
                actions.postValue(Actions.TOAST_ERROR_SIGNING_UP);
            }
        });
    }

    /**
     * Actions sent to Fragment for 'one-shot' events
     */
    enum Actions {
        TOAST_ALL_FIELDS_REQUIRED,
        TOAST_ERROR_SIGNING_UP,
        NAVIGATE_TO_ONBOARDING_ACTIVITY
    }
}
