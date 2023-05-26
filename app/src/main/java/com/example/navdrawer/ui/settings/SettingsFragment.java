package com.example.navdrawer.ui.settings;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navdrawer.ChangePassActivity;
import com.example.navdrawer.MainActivity;
import com.example.navdrawer.ProfileActivity;
import com.example.navdrawer.R;
import com.example.navdrawer.ui.login.LoginActivity;
import com.example.navdrawer.ui.util.userDataUtil;
import com.google.firebase.auth.FirebaseAuth;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SettingsFragment extends Fragment {

    private FirebaseAuth auth;
    private SettingsViewModel settingsViewModel;
    private TextView profile, changePass, privacyPolicy, aboutUs, logOut;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        View settingsFrag = inflater.inflate(R.layout.fragment_settings, container, false);
        return settingsFrag;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profile = view.findViewById(R.id.profile);
        changePass = view.findViewById(R.id.changePass);
        privacyPolicy = view.findViewById(R.id.privacyPolicy);
        aboutUs = view.findViewById(R.id.aboutUs);
        logOut = view.findViewById(R.id.logOut);
        auth = FirebaseAuth.getInstance();

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ChangePassActivity.class));
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_policy);
            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.nav_about_us);
            }
        });


    }

    public void logout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Are you sure, You wanted to logout");
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                userDataUtil.setLoggedIn(getApplicationContext(), false);
                auth.signOut();
                getActivity().finishAffinity();
                startActivity(new Intent(getContext(), LoginActivity.class));
                Toast.makeText(getContext(), "You are logged out !", Toast.LENGTH_LONG).show();
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

}