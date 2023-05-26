package com.example.navdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.navdrawer.ui.SearchCar.SearchCarModel;
import com.example.navdrawer.ui.SearchCar.SelectCarActivity;
import com.example.navdrawer.ui.SearchCar.scAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarDetails extends AppCompatActivity {

    private FirebaseFirestore db;
    SimpleDraweeView images;
    TextView carName,carCompany,acceleration,speed,seats,ac,fuel,music,gears,style;
    RatingBar rating;
    ChipGroup chipGroup;
    TextView buttonR;
    SearchCarModel m ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        images = findViewById(R.id.carImages);
        carName = findViewById(R.id.carName);
        carCompany = findViewById(R.id.carCompany);
        acceleration = findViewById(R.id.acceleration);
        speed = findViewById(R.id.speed);
        seats = findViewById(R.id.seats);
        ac = findViewById(R.id.ac);
        fuel = findViewById(R.id.fuel);
        music = findViewById(R.id.music);
        gears = findViewById(R.id.gears);
        style = findViewById(R.id.style);
        rating = findViewById(R.id.ratingBar);
        chipGroup = findViewById(R.id.chipGroup);

        buttonR=findViewById(R.id.rightButton);
        buttonR.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarDetails.this,BookCarRide.class);
                intent.putExtra("carId",getIntent().getStringExtra("carId"));
                intent.putExtra("carImage",m.getCarImage());
                intent.putExtra("carName",m.getCarName());
                intent.putExtra("carCompany",m.getCarCompany());
//                intent.putExtra("carRate",m.getCarRate());
                intent.putExtra("seats",m.getSeats());
                intent.putExtra("ac",m.getAc());
                intent.putExtra("type",m.getType());
                startActivity(intent);
            }
        });


        db = FirebaseFirestore.getInstance();
        db.collection("cars").document(getIntent().getStringExtra("carId"))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String imgUrls= document.get("images").toString();
                                String urlStr= imgUrls.substring(1,imgUrls.length()-1);
                                String[] urls =urlStr.split(",");

                                images.setImageURI(Uri.parse(urls[0]));
                                carName.setText(document.getString("name"));
                                carCompany.setText(document.getString("company"));
                                acceleration.setText(document.get("acceleration").toString()+" m/s2");
                                speed.setText(document.get("topSpeed").toString()+" Km/h");
                                seats.setText(document.get("seats").toString()+" Adults");
                                ac.setText(document.getString("ac"));
                                fuel.setText(document.getString("fuel"));
                                music.setText(document.getString("music"));
                                style.setText(document.getString("style"));
                                gears.setText(document.getString("gears"));
                                rating.setRating(document.getDouble("avgRating").floatValue());

                                String str= document.get("purpose").toString();
                                String str1= str.substring(1,str.length()-1);
                                String[] purpose = str1.split(",");

                                for(String s:purpose) {
                                    Chip chip =new Chip(CarDetails.this);
                                    chip.setText(s);
                                    chipGroup.addView(chip);
                                }

                                m= new SearchCarModel(
                                        getIntent().getStringExtra("carId"),
                                        urls[0],
                                        document.getString("name"),
                                        document.getString("company"),
                                        document.get("seats").toString()+" Adults",
                                        document.getString("ac"),
                                        document.getString("style"),
                                        document.getString("gears"),
                                        30
                                );


                            } else {
                                Log.e("TAG", "No such document");
                            }


                        } else {
                            Log.e("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}