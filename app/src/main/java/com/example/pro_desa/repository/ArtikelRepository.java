package com.example.pro_desa.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.model.Artikel;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.network.response.ArtikelResponse;
import com.example.pro_desa.utils.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtikelRepository {
    private MutableLiveData<ArrayList<Artikel>> artikelLiveData;
    private ApiInterface apiInterface;
    private SharedPrefManager sharedPrefManager;

    public ArtikelRepository() {
        sharedPrefManager = new SharedPrefManager(Myapp.getContext());
        artikelLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getArtikel(){
        Call<ArtikelResponse> getArtikel = apiInterface.getArtikel(
                sharedPrefManager.getSpAppToken(),
                sharedPrefManager.getSpProdesaToken(),
                sharedPrefManager.getSpDesaCode()
        );
        getArtikel.enqueue(new Callback<ArtikelResponse>() {
            @Override
            public void onResponse(Call<ArtikelResponse> call, Response<ArtikelResponse> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    artikelLiveData.postValue(response.body().getArtikelList().getArtikels());
                    Log.d("isissssisiis", String.valueOf(response.body().getArtikelList().getArtikelUrl() != null));
                    for (Artikel artikel: response.body().getArtikelList().getArtikels()){
//                        artikel.getJudul();
                        Log.d("judul", artikel.getJudul());
                    }

                }
            }

            @Override
            public void onFailure(Call<ArtikelResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<ArrayList<Artikel>> getArtikelResponseLiveData() {
        return artikelLiveData;
    }
}
