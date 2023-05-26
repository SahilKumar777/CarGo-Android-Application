package com.example.navdrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ChangePassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}