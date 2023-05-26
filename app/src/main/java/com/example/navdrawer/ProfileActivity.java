package com.example.navdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navdrawer.ui.login.LoginActivity;
import com.facebook.login.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private ImageView profileImage;
    private TextView Username;
    private EditText uName,email,contact,dob,address;
    private Button editButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth=FirebaseAuth.getInstance();    //Getting instance of FirebaseAuth

        // if user not logged in, go to sign-in screen
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        profileImage = findViewById(R.id.profileImage);
        Username = findViewById(R.id.profileName);
        uName = findViewById(R.id.personName);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.phone);
        dob = findViewById(R.id.dob);
        address = findViewById(R.id.address);
        editButton = findViewById(R.id.editButton);

        FirebaseUser user = auth.getCurrentUser();

        profileImage.setImageURI(user.getPhotoUrl());
        Username.setText(user.getDisplayName());
        uName.setText(user.getDisplayName());
        email.setText(user.getEmail());
        contact.setText(user.getPhoneNumber());
        dob.setText("01/01/1900");
        address.setText("Address");

        editButton.setOnClickListener(v -> {
            Toast.makeText(this,"Cannot be edited",Toast.LENGTH_SHORT).show();
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        // if user not logged in, go to sign-in screen
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}