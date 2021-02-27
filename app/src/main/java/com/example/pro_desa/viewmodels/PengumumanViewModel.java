package com.example.pro_desa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pro_desa.model.Pengumuman;
import com.example.pro_desa.repository.PengumumanRepository;

import java.util.ArrayList;

public class PengumumanViewModel extends AndroidViewModel {
    private PengumumanRepository pengumumanRepository;
    private LiveData<ArrayList<Pengumuman>> pengumumanLiveData;

    public PengumumanViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        pengumumanRepository = new PengumumanRepository();
        pengumumanLiveData = pengumumanRepository.getPengumumanResponseLiveData();
    }

    public void getPengumuman() {
        pengumumanRepository.getPengumuman();
    }

    public LiveData<ArrayList<Pengumuman>> getPengumumanLiveData() {
//        Log.d("live_data", String.valueOf(pengumumanLiveData));
        return pengumumanLiveData;
    }
}
