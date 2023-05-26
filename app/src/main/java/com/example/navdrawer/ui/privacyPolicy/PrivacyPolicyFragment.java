package com.example.navdrawer.ui.privacyPolicy;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navdrawer.R;

public class PrivacyPolicyFragment extends Fragment {

    private PrivacyPolicyViewModel mViewModel;

    public static PrivacyPolicyFragment newInstance() {
        return new PrivacyPolicyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(PrivacyPolicyViewModel.class);
        View PP= inflater.inflate(R.layout.fragment_privacy_policy, container, false);
        return PP;
    }



}