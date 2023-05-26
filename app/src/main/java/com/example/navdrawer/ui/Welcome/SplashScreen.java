package com.example.navdrawer.ui.Welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.navdrawer.MainActivity;
import com.example.navdrawer.R;

public class SplashScreen extends AppCompatActivity {

    private PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Checking for first time launch
        prefManager = new PrefManager(this);

        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(1200);  //Delay of 1.2 seconds
                } catch (Exception e) {

                } finally {

                    if (prefManager.isFirstTimeLaunch()) {
                        startActivity(new Intent(SplashScreen.this, WelcomeScreen.class));
                        finish();
                    }
                    else{
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                        finish();
                    }
                }
            }
        };

        welcomeThread.start();
    }
}