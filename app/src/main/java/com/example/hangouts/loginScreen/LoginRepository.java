package com.example.hangouts.loginScreen;


public class LoginRepository {
    private static LoginRepository instance;

    public static LoginRepository getInstance(){
        if (instance == null){
            instance = new LoginRepository();
        }
        return instance;
    }


}
