package com.example.pro_desa.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.model.SyaratPermohonanSuratList;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.network.response.SyaratPermohonanSuratResponse;
import com.example.pro_desa.utils.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyaratPermohonanSuratRepository {
    private MutableLiveData<ArrayList<SyaratPermohonanSuratList>> syaratpermohonanSuratLiveData;
    private ApiInterface apiInterface;
    private SharedPrefManager sharedPrefManager;

    public SyaratPermohonanSuratRepository() {
        sharedPrefManager = new SharedPrefManager(Myapp.getContext());
        syaratpermohonanSuratLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getSyaratPermohonanSurat(){
        Call<SyaratPermohonanSuratResponse> getPermohonanSurat = apiInterface.getSyaratPermohonanSurat(
                sharedPrefManager.getSpAppToken(),
                sharedPrefManager.getSpProdesaToken(),
                sharedPrefManager.getSpDesaCode(),
                sharedPrefManager.getSpIdLeter()
        );
        getPermohonanSurat.enqueue(new Callback<SyaratPermohonanSuratResponse>() {
            @Override
            public void onResponse(Call<SyaratPermohonanSuratResponse> call, Response<SyaratPermohonanSuratResponse> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    syaratpermohonanSuratLiveData.postValue(response.body().getSyaratPermohonanSuratLists());
                    Log.d("isissssisiis", String.valueOf(response.body().getSyaratPermohonanSuratLists() != null));
                    for (SyaratPermohonanSuratList permohonanSuratList : response.body().getSyaratPermohonanSuratLists()){
//                        permohonanSurat.getJudul();
//                        Log.d("judul", permohonanSurat.getJudul());
                    }

                }
            }

            @Override
            public void onFailure(Call<SyaratPermohonanSuratResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<ArrayList<SyaratPermohonanSuratList>> getSyaratPermohonanSuratResponseLiveData() {
        return syaratpermohonanSuratLiveData;
    }
}
