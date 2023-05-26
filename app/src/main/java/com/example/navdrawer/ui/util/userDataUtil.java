package com.example.navdrawer.ui.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Map;

public class userDataUtil {
    private Context context;
    public final static String PREFS_NAME = "UserDataPrefs";

    public userDataUtil(Context context){
        this.context = context;
    }

    public void setUserData(String userId, String uName,String url,Boolean logIn){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userId",userId);
        editor.putString("userName",uName);
        editor.putString("imageUrl",url);
        editor.putBoolean("isLogIn",logIn);

        editor.apply();
    }

    public Map<String,?> getUserData(){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return prefs.getAll();
    }

    static public String getUserId(Context context1){
        SharedPreferences prefs = context1.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return prefs.getString("userId","NA");
    }

    static public Boolean isUserLogin(Context context1){
        SharedPreferences prefs = context1.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return prefs.getBoolean("isLogIn",false);
    }

    static public void setLoggedIn(Context context1,Boolean val){
        SharedPreferences prefs = context1.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLogIn",val);

        editor.apply();
    }




}
