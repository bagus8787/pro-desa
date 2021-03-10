package com.example.pro_desa.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.model.PermohonanSurat;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.network.response.PermohonanSuratResponse;
import com.example.pro_desa.utils.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PermohonanSuratRepository {
    private MutableLiveData<ArrayList<PermohonanSurat>> permohonanSuratLiveData;
    private ApiInterface apiInterface;
    private SharedPrefManager sharedPrefManager;

    public PermohonanSuratRepository() {
        sharedPrefManager = new SharedPrefManager(Myapp.getContext());
        permohonanSuratLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getPermohonanSurat(){
        Call<PermohonanSurat> getPermohonanSurat = apiInterface.getPermohonanSurat(
                sharedPrefManager.getSpAppToken(),
                sharedPrefManager.getSpProdesaToken(),
                sharedPrefManager.getSpDesaCode(),
                sharedPrefManager.getSpNik()
        );
        getPermohonanSurat.enqueue(new Callback<PermohonanSurat>() {
            @Override
            public void onResponse(Call<PermohonanSurat> call, Response<PermohonanSurat> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    permohonanSuratLiveData.postValue(response.body().getPermohonanSurats());
//                    Log.d("cdsdfcsd", String.valueOf(response.body().getPermohonanSurats() != null));
                    for (PermohonanSurat permohonanSuratList : response.body().getPermohonanSurats()){
//                        permohonanSurat.getJudul();
//                        Log.d("dfsdfsd", permohonanSuratList.getNama_kategori_surat());
                    }

                }
            }

            @Override
            public void onFailure(Call<PermohonanSurat> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<ArrayList<PermohonanSurat>> getPermohonanSuratResponseLiveData() {
        return permohonanSuratLiveData;
    }
}
