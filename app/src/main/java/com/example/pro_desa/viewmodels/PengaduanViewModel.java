package com.example.pro_desa.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pro_desa.model.Pengaduan;
import com.example.pro_desa.repository.PengaduanRepository;

import java.util.ArrayList;

public class PengaduanViewModel extends AndroidViewModel {
    private PengaduanRepository pengaduanRepository;
    private LiveData<ArrayList<Pengaduan>> pengaduanLiveData;

    public PengaduanViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        pengaduanRepository = new PengaduanRepository();
        pengaduanLiveData = pengaduanRepository.getPengaduanResponseLiveData();
    }

    public void getPengaduan() {
        pengaduanRepository.getPengaduan();
    }

    public LiveData<ArrayList<Pengaduan>> getPengaduanLiveData() {
        Log.d("live_data", String.valueOf(pengaduanLiveData));
        return pengaduanLiveData;
    }
}
