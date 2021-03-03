package com.example.pro_desa.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.model.PermohonanSuratList;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.network.response.PermohonanSuratResponse;
import com.example.pro_desa.utils.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PermohonanSuratRepository {
    private MutableLiveData<ArrayList<PermohonanSuratList>> permohonanSuratLiveData;
    private ApiInterface apiInterface;
    private SharedPrefManager sharedPrefManager;

    public PermohonanSuratRepository() {
        sharedPrefManager = new SharedPrefManager(Myapp.getContext());
        permohonanSuratLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getPermohonanSurat(){
        Call<PermohonanSuratResponse> getPermohonanSurat = apiInterface.getPermohonanSurat(
                sharedPrefManager.getSpAppToken(),
                sharedPrefManager.getSpProdesaToken(),
                sharedPrefManager.getSpDesaCode()
        );
        getPermohonanSurat.enqueue(new Callback<PermohonanSuratResponse>() {
            @Override
            public void onResponse(Call<PermohonanSuratResponse> call, Response<PermohonanSuratResponse> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    permohonanSuratLiveData.postValue(response.body().getPermohonanSuratLists());
//                    Log.d("isissssisiis", String.valueOf(response.body().getArtikelList().getArtikelUrl() != null));
                    for (PermohonanSuratList permohonanSuratList : response.body().getPermohonanSuratLists()){
//                        permohonanSurat.getJudul();
//                        Log.d("judul", permohonanSurat.getJudul());
                    }

                }
            }

            @Override
            public void onFailure(Call<PermohonanSuratResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<ArrayList<PermohonanSuratList>> getPermohonanSuratResponseLiveData() {
        return permohonanSuratLiveData;
    }
}
