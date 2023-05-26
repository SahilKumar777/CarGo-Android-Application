package com.example.navdrawer.ui.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateUtils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class BookFormData {
    private Context context;
    public final static String PREFS_NAME = "BookFormPrefs";

    public BookFormData(Context context){
        this.context = context;
    }

    public void setFormsData(BookFormDataModel data){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("from",data.getFrom());
        editor.putString("to",data.getTo());
        editor.putString("dateP",data.getDateP());
        editor.putString("timeP",data.getTimeP());
        editor.putBoolean("isReturn",data.getIsReturn());
        if (data.getIsReturn()){
            editor.putString("returnTo",data.getReturnTo());
            editor.putString("dateR",data.getDateR());
            editor.putString("timeR",data.getTimeR());
        }
        editor.apply();
    }

    public BookFormDataModel getFormsData(){
        BookFormDataModel data = new BookFormDataModel();
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        data.setFrom(prefs.getString("from","NA"));
        data.setTo(prefs.getString("to","NA"));
        data.setDateP(prefs.getString("dateP","NA"));
        data.setTimeP(prefs.getString("timeP","NA"));
        data.setIsReturn(prefs.getBoolean("isReturn",false));
        if(data.getIsReturn()) {
            data.setReturnTo(prefs.getString("returnTo","NA"));
            data.setDateR(prefs.getString("dateR","NA"));
            data.setTimeR(prefs.getString("timeR","NA"));

        }

        return data;
    }

    public void clearData(){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}
