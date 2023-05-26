package com.example.navdrawer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Carousel;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.navdrawer.ui.crousel.MyCarouselAdapter;

public class SaleOldCar extends AppCompatActivity {

    Carousel carousel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_old_car);

        carousel =findViewById(R.id.carousel);
        carousel.setAdapter(new MyCarouselAdapter());


    }
}