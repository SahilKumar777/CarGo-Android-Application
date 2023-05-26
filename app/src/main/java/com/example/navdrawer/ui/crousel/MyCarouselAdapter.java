package com.example.navdrawer.ui.crousel;

import android.view.View;
import android.widget.ImageView;

import com.example.navdrawer.R;

import androidx.constraintlayout.helper.widget.Carousel;

public class MyCarouselAdapter implements Carousel.Adapter {
    @Override
    public int count() {
        return 3;
    }

    @Override
    public void populate(View view, int index) {

            ImageView imageView = (ImageView) view;
            if(index==0) {
                imageView.setImageResource(R.drawable.black_bugatti);
            }
        if(index==1) {
            imageView.setImageResource(R.drawable.bluecar);
        }
        if(index==2) {
            imageView.setImageResource(R.drawable.car4);
        }


    }

    @Override
    public void onNewItem(int index) {

    }
}
