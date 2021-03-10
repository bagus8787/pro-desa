package com.example.pro_desa.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.model.ListPermohonanSuratList;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.network.response.ListPermohonanSuratResponse;
import com.example.pro_desa.utils.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PermohonanSuratKategoriRepository {
    private MutableLiveData<ArrayList<ListPermohonanSuratList>> permohonanSuratLiveData;
    private ApiInterface apiInterface;
    private SharedPrefManager sharedPrefManager;

    public PermohonanSuratKategoriRepository() {
        sharedPrefManager = new SharedPrefManager(Myapp.getContext());
        permohonanSuratLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getPermohonanSurat(){
        Call<ListPermohonanSuratResponse> getPermohonanSurat = apiInterface.getPermohonanSuratKategori(
                sharedPrefManager.getSpAppToken(),
                sharedPrefManager.getSpProdesaToken(),
                sharedPrefManager.getSpDesaCode()
        );
        getPermohonanSurat.enqueue(new Callback<ListPermohonanSuratResponse>() {
            @Override
            public void onResponse(Call<ListPermohonanSuratResponse> call, Response<ListPermohonanSuratResponse> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    permohonanSuratLiveData.postValue(response.body().getPermohonanSuratLists());
//                    Log.d("isissssisiis", String.valueOf(response.body().getArtikelList().getArtikelUrl() != null));
                    for (ListPermohonanSuratList permohonanSuratList : response.body().getPermohonanSuratLists()){
//                        permohonanSurat.getJudul();
//                        Log.d("judul", permohonanSurat.getJudul());
                    }

                }
            }

            @Override
            public void onFailure(Call<ListPermohonanSuratResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<ArrayList<ListPermohonanSuratList>> getPermohonanSuratResponseLiveData() {
        return permohonanSuratLiveData;
    }
}
