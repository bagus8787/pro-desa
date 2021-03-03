package com.example.pro_desa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pro_desa.model.PermohonanSuratList;
import com.example.pro_desa.repository.PermohonanSuratRepository;

import java.util.ArrayList;

public class PermohonanSuratViewModel extends AndroidViewModel {
    private PermohonanSuratRepository permohonanSuratRepository;
    private LiveData<ArrayList<PermohonanSuratList>> permohonanSuratLiveData;

    public PermohonanSuratViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        permohonanSuratRepository = new PermohonanSuratRepository();
        permohonanSuratLiveData = permohonanSuratRepository.getPermohonanSuratResponseLiveData();
    }

    public void getPermohonanSurat() {
        permohonanSuratRepository.getPermohonanSurat();
    }

    public LiveData<ArrayList<PermohonanSuratList>> getPermohonanSuratLiveData() {
//        Log.d("live_data", String.valueOf(permohonanSuratLiveData));
        return permohonanSuratLiveData;
    }
}
