package com.example.navdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.navdrawer.ui.login.LoginActivity;
import com.example.navdrawer.ui.util.userDataUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {


    private FirebaseAuth auth;
    private EditText name,email,contact,password,confirmPassword;
    private CheckBox checkBox;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth=FirebaseAuth.getInstance();    //Getting instance of FirebaseAuth

        // if user logged in, go to main screen
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        name=findViewById(R.id.name);
        email=findViewById(R.id.inputEmail);
        contact=findViewById(R.id.editMobile);
        password=findViewById(R.id.password);
        confirmPassword=findViewById(R.id.confirmPassword);
        checkBox = findViewById(R.id.checkBox);
        progressBar=findViewById(R.id.progressBar);


    }

    @Override
    protected void onStart() {
        super.onStart();

        // if user logged in, go to sign-in screen
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    public  void onRegisterClick(View v){

        String username = name.getText().toString().trim();
        String emailInput = email.getText().toString().trim();
        String Mobile=contact.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String confPass=confirmPassword.getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            name.setError("Field Cannot be empty");
            Toast.makeText(this,"Enter name",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(emailInput)){
            email.setError("Field Cannot be empty");
            Toast.makeText(this,"Enter Email id",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(Mobile)){
            contact.setError("Field Cannot be empty");
            Toast.makeText(this,"Enter Contact no",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            password.setError("Password Cannot be empty");
            Toast.makeText(this,"Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(confPass)){
            confirmPassword.setError("Password Cannot be empty");
            Toast.makeText(this,"Enter Confirmation Password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(pass.length()<6){
            password.setError("Password Should be greater than 6 words");
            Toast.makeText(this,"Small Password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isValidEmailId(emailInput)){
            email.setError("Enter a valid Email Id");
            Toast.makeText(this,"Invalid Email id",Toast.LENGTH_SHORT).show();
            return;
        }

        if(! pass.equals(confPass)){
            confirmPassword.setError("Password didn't matched");
            Toast.makeText(this,"Confirm password not matched",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!checkBox.isChecked()){
            checkBox.setError("Accept the terms and conditions to continue");
            Toast.makeText(this,"Accept T & C",Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        //create user with email/password by adding complete listener
        auth.createUserWithEmailAndPassword(emailInput, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                        // If sign-in fails, display a message to the user. If sign-in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {

                            Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException().toString().split(":")[1],
                                    Toast.LENGTH_LONG).show();
                            Log.e("Authentication failed", task.getException().toString());
                        } else {

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .setPhotoUri(Uri.parse("https://cdn1.iconfinder.com/data/icons/web-and-networking-1/128/6-512.png"))
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.e("data util","Set ok");
                                                new userDataUtil(SignUpActivity.this).setUserData(user.getEmail(),user.getDisplayName(),user.getPhotoUrl().toString(),true);
                                            }
                                        }
                                    });
                            Log.e("activity","stated");
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });

    }

    public void onSignInClick(View view){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

}