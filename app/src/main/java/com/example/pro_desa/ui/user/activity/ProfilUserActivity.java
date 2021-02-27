package com.example.pro_desa.ui.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.R;
import com.example.pro_desa.model.User;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.utils.SharedPrefManager;
import com.example.pro_desa.viewmodels.ProfileViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class ProfilUserActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;
    Context mContext;

//    ProfileRepository profileRepository;
    ProfileViewModel profileViewModel;

    TextInputEditText txt_nik, txt_nama, txt_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_user);

        mContext = this;

        sharedPrefManager = new SharedPrefManager(Myapp.getContext());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        txt_nik     = findViewById(R.id.ip_nik_pl);
        txt_nama    = findViewById(R.id.ip_nama_pl);
        txt_email   = findViewById(R.id.ip_email_pl);

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.init();
        profileViewModel.getProfileResponseLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User response) {
                if (response != null){
                    sharedPrefManager.setSpNama(response.getFullname());
                    txt_nik.setText(sharedPrefManager.getSpNik());
                    txt_nama.setText(sharedPrefManager.getSpNama());
                    txt_email.setText(sharedPrefManager.getSpEmail());

                    Log.d("responses", response.getNik());
                }
            }
        });

        txt_nik.setText(sharedPrefManager.getSpNik());
        txt_nama.setText(sharedPrefManager.getSpNama());
        txt_email.setText(sharedPrefManager.getSpEmail());


    }

    @Override
    public void onResume(){
        super.onResume();
        profileViewModel.getProfile();
    }
}