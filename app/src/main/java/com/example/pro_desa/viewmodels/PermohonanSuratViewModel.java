package com.example.pro_desa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pro_desa.model.ListPermohonanSuratList;
import com.example.pro_desa.model.PermohonanSurat;
import com.example.pro_desa.repository.PermohonanSuratKategoriRepository;
import com.example.pro_desa.repository.PermohonanSuratRepository;

import java.util.ArrayList;

public class PermohonanSuratViewModel extends AndroidViewModel {
    private PermohonanSuratRepository permohonanSuratKategoriRepository;
    private LiveData<ArrayList<PermohonanSurat>> permohonanSuratLiveData;

    public PermohonanSuratViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        permohonanSuratKategoriRepository = new PermohonanSuratRepository();
        permohonanSuratLiveData = permohonanSuratKategoriRepository.getPermohonanSuratResponseLiveData();
    }

    public void getPermohonanSurat() {
        permohonanSuratKategoriRepository.getPermohonanSurat();
    }

    public LiveData<ArrayList<PermohonanSurat>> getPermohonanSuratLiveData() {
//        Log.d("live_data", String.valueOf(permohonanSuratLiveData));
        return permohonanSuratLiveData;
    }
}
