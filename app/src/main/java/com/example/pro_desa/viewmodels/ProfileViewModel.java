package com.example.pro_desa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pro_desa.model.User;
import com.example.pro_desa.repository.ProfileRepository;

public class ProfileViewModel extends AndroidViewModel {
    private ProfileRepository profileRepository;
    private LiveData<User> profileLiveData;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        profileRepository = new ProfileRepository();
        profileLiveData = profileRepository.getProfileResponseLiveData();
    }

    public void getProfile() {
        profileRepository.getProfile();
    }

    public LiveData<User> getProfileResponseLiveData() {
        return profileLiveData;
    }
}
