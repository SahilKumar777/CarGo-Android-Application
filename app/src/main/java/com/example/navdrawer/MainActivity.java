package com.example.navdrawer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navdrawer.ui.login.LoginActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.InputStream;
import java.net.URL;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.navdrawer.ui.util.userDataUtil;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);

        setContentView(R.layout.activity_main);

        auth=FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        // If user logged in, go to sign-in screen
        if (user == null) {
            userDataUtil.setLoggedIn(this,false);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }



        // ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Floating Button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Drawer Layout
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_settings,R.id.nav_logout)
                //.setDrawerLayout(drawer)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(menuItem -> {
            logout(navigationView);
            return true;
        });

        updateNavHeader();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void logout(View v){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to logout");
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                userDataUtil.setLoggedIn(getApplicationContext(),false);
                auth.signOut();
                finishAffinity();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                Toast.makeText(MainActivity.this,"You are logged out !",Toast.LENGTH_LONG).show();
                Snackbar.make(v,"Logged Out Successfully !!", BaseTransientBottomBar.LENGTH_LONG).show();
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


    public void updateNavHeader() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView= navigationView.getHeaderView(0);
        TextView navName= headerView.findViewById(R.id.uName);
        TextView navEmail=headerView.findViewById(R.id.email);
        SimpleDraweeView navImage=headerView.findViewById(R.id.imageView);
        if (auth.getCurrentUser() != null) {
            navName.setText(auth.getCurrentUser().getDisplayName());
            navEmail.setText(auth.getCurrentUser().getEmail());
            navImage.setImageURI(auth.getCurrentUser().getPhotoUrl());
        }

    }

}