package com.example.navdrawer.ui.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navdrawer.ChangePassActivity;
import com.example.navdrawer.MainActivity;
import com.example.navdrawer.R;
import com.example.navdrawer.SignUpActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import com.example.navdrawer.ui.util.userDataUtil;
public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private FirebaseAuth auth;

    private ProgressBar loadingProgressBar;
    Button loginButton;
    EditText passwordEditText;
    EditText usernameEditText;
    TextView buttonFB;
    private final static int RC_SIGN_IN = 123;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth=FirebaseAuth.getInstance();
        // If user logged in, go to sign-in screen
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        buttonFB=findViewById(R.id.facebook);
        loadingProgressBar = findViewById(R.id.loading);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }

    //Checking current user is logging or not
    @Override
    public void onStart() {
        super.onStart();
        // If user logged in, go to sign-in screen
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadingProgressBar.setVisibility(View.GONE);
    }

    public void onSignInClick(View view){
        String emailInput = usernameEditText.getText().toString().trim();
        String pass = passwordEditText.getText().toString().trim();

        if(TextUtils.isEmpty(emailInput)){
            usernameEditText.setError("Email cannot be empty");
            Toast.makeText(this,"Enter Email id",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(pass)){
            passwordEditText.setError("Password cannot be empty");
            Toast.makeText(this,"Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }

        loadingProgressBar.setVisibility(View.VISIBLE);

        //authenticate user with email/password by adding complete listener
        auth.signInWithEmailAndPassword(emailInput, pass)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        loadingProgressBar.setVisibility(View.GONE);

                        if (!task.isSuccessful()) {
                            // there was an error
                            Toast.makeText(LoginActivity.this, "Network Error " +task.getException().toString().split(":")[1] ,
                                    Toast.LENGTH_LONG).show();
                            Log.e("Network Error", task.getException().toString());

                        } else {

                            String photoUrl = "https://cdn1.iconfinder.com/data/icons/web-and-networking-1/128/6-512.png";
                            if (auth.getCurrentUser().getPhotoUrl()!=null){
                                photoUrl=auth.getCurrentUser().getPhotoUrl().toString();
                            }
                            userDataUtil userData = new userDataUtil(LoginActivity.this);
                            userData.setUserData(auth.getCurrentUser().getEmail(),auth.getCurrentUser().getDisplayName(),photoUrl,true);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    // On Click SignUp button
    public void onSignUpClick(View view){
        startActivity(new Intent(this, SignUpActivity.class));
//        finish();
    }

    // On Click Forgot Password
    public void onForgotPassClick(View view){
        startActivity(new Intent(this, ChangePassActivity.class));
//        finish();
    }

    // On Click Google SignIn Button
    public void onGoogleClick(View view){
        signIn();
    }

    // On Click Facebook SignIn Button
    public void onFbClick(View view){

    }



    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

                // Google Sign In failed, update UI appropriately
                Log.w("LoginPage", "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LoginActivityGoogle", "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();

                            userDataUtil userData = new userDataUtil(LoginActivity.this);
                            userData.setUserData(user.getUid(),user.getDisplayName(),user.getPhotoUrl().toString(),true);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LoginActivityGoogle", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Auth Fail", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

}