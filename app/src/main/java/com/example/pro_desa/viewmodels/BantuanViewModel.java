package com.example.pro_desa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pro_desa.model.Bantuan;
import com.example.pro_desa.repository.BantuanRepository;

import java.util.ArrayList;

public class BantuanViewModel extends AndroidViewModel {
    private BantuanRepository bantuanRepository;
    private LiveData<ArrayList<Bantuan>> bantuanLiveData;

    public BantuanViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        bantuanRepository = new BantuanRepository();
        bantuanLiveData = bantuanRepository.getBantuanResponseLiveData();
    }

    public void getBantuan() {
        bantuanRepository.getBantuan();
    }

    public LiveData<ArrayList<Bantuan>> getArtikelLiveData() {
//        Log.d("live_data", String.valueOf(bantuanLiveData));
        return bantuanLiveData;
    }
}
