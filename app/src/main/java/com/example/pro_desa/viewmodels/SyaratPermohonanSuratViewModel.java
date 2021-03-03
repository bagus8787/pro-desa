package com.example.pro_desa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pro_desa.model.SyaratPermohonanSuratList;
import com.example.pro_desa.repository.SyaratPermohonanSuratRepository;

import java.util.ArrayList;

public class SyaratPermohonanSuratViewModel extends AndroidViewModel {
    private SyaratPermohonanSuratRepository syaratPermohonanSuratRepository;
    private LiveData<ArrayList<SyaratPermohonanSuratList>> syaratpermohonanSuratLiveData;

    public SyaratPermohonanSuratViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        syaratPermohonanSuratRepository = new SyaratPermohonanSuratRepository();
        syaratpermohonanSuratLiveData = syaratPermohonanSuratRepository.getSyaratPermohonanSuratResponseLiveData();
    }

    public void getSyaratPermohonanSurat() {
        syaratPermohonanSuratRepository.getSyaratPermohonanSurat();
    }

    public LiveData<ArrayList<SyaratPermohonanSuratList>> getSyaratPermohonanSuratLiveData() {
//        Log.d("live_data", String.valueOf(syaratpermohonanSuratLiveData));
        return syaratpermohonanSuratLiveData;
    }
}
