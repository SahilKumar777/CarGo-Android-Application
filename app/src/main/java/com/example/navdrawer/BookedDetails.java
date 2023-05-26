package com.example.navdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navdrawer.ui.SearchCar.SearchCarModel;
import com.example.navdrawer.ui.login.LoginActivity;
import com.example.navdrawer.ui.myBookings.MyBookings;
import com.example.navdrawer.ui.myBookings.MyBookingsFragmentAdapter;
import com.example.navdrawer.ui.myBookings.MyBookingsModel;
import com.example.navdrawer.ui.util.DateTimeUtil;
import com.example.navdrawer.ui.util.userDataUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class BookedDetails extends AppCompatActivity {

    private FirebaseFirestore db;
    TextView from,to,date,time,passengers,price;
    SwitchCompat rSwitch;
    ProgressBar pb;
    Chip status;

    SimpleDraweeView images;
    TextView carName,carCompany,acceleration,speed,seats,ac,fuel,music,gears,style;
    RatingBar rating;
    ChipGroup chipGroup;
    Button cancelBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pb = findViewById(R.id.progressBD);
        pb.setVisibility(View.VISIBLE);

        from = findViewById(R.id.fromLoc);
        to = findViewById(R.id.toLoc);
        date = findViewById(R.id.dateVal);
        time = findViewById(R.id.timeVal);
        passengers = findViewById(R.id.passengerVal);
        price = findViewById(R.id.price);
        rSwitch = findViewById(R.id.returnSwitch);
        status = findViewById(R.id.status);

        // Car Details
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
        cancelBooking = findViewById(R.id.cancel);

        db = FirebaseFirestore.getInstance();

        setBookingData();
        setCarData();

        cancelBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(v);

            }
        });

    }

    private void setBookingData(){
        db.collection("bookings").document(getIntent().getStringExtra("bookingId"))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                from.setText(document.getString("from"));
                                to.setText(document.getString("to"));
                                date.setText(DateTimeUtil.dateToStrDate(document.getDate("dateP")));
                                time.setText(DateTimeUtil.dateToStrTime(document.getDate("dateP")));
                                passengers.setText(document.get("passenger").toString());
                                price.setText("Rs " + document.get("fare").toString());
                                rSwitch.setChecked(document.getBoolean("isReturn"));

                                String bookingStatus = document.getString("status");
                                status.setText(bookingStatus);

                                if(bookingStatus != "pending"){
                                    cancelBooking.setClickable(false);
                                }

                                pb.setVisibility(View.GONE);
                            }
                            else{Log.e("TAG", "Error Zero documents"); }

                        }
                        else{
                            Log.e("TAG", "Error getting booking documents", task.getException());
                        }
                    }
                });
    }

    private void setCarData(){

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
                                    Chip chip =new Chip(BookedDetails.this);
                                    chip.setText(s);
                                    chipGroup.addView(chip);
                                }

                            } else {
                                Log.e("TAG", "No such document");
                            }


                        } else {
                            Log.e("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    void showDialog(View v){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to Cancel this Booking");
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                db.collection("bookings").document(getIntent().getStringExtra("bookingId"))
                    .update("status", "cancelled")
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Snackbar.make(v,"Your booking Cancelled!",BaseTransientBottomBar.LENGTH_LONG).show();
//                            Toast.makeText(BookedDetails.this,"Your booking Cancelled!",Toast.LENGTH_LONG).show();
                            Log.d("success", "DocumentSnapshot successfully updated!");
                            cancelBooking.setClickable(false);
                            status.setText("Cancelled");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("Failure", "Error updating document", e);
                            Toast.makeText(BookedDetails.this,"Not Cancelled!",Toast.LENGTH_LONG).show();
                        }
                    });

            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}