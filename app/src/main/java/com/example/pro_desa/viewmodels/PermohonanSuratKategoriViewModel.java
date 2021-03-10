package com.example.pro_desa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pro_desa.model.ListPermohonanSuratList;
import com.example.pro_desa.repository.PermohonanSuratKategoriRepository;

import java.util.ArrayList;

public class PermohonanSuratKategoriViewModel extends AndroidViewModel {
    private PermohonanSuratKategoriRepository permohonanSuratKategoriRepository;
    private LiveData<ArrayList<ListPermohonanSuratList>> permohonanSuratLiveData;

    public PermohonanSuratKategoriViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        permohonanSuratKategoriRepository = new PermohonanSuratKategoriRepository();
        permohonanSuratLiveData = permohonanSuratKategoriRepository.getPermohonanSuratResponseLiveData();
    }

    public void getPermohonanSurat() {
        permohonanSuratKategoriRepository.getPermohonanSurat();
    }

    public LiveData<ArrayList<ListPermohonanSuratList>> getPermohonanSuratLiveData() {
//        Log.d("live_data", String.valueOf(permohonanSuratLiveData));
        return permohonanSuratLiveData;
    }
}
