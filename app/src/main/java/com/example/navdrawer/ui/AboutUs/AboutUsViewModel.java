package com.example.navdrawer.ui.AboutUs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutUsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AboutUsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is About us fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}