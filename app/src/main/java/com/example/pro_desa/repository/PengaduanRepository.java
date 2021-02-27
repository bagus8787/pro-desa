package com.example.pro_desa.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.model.Pengaduan;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.network.response.PengaduanResponse;
import com.example.pro_desa.utils.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengaduanRepository {
    private MutableLiveData<ArrayList<Pengaduan>> pengaduanLiveData;
    private ApiInterface apiInterface;
    private SharedPrefManager sharedPrefManager;

    public PengaduanRepository() {
        sharedPrefManager = new SharedPrefManager(Myapp.getContext());
        pengaduanLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getPengaduan(){
        Call<PengaduanResponse> getArtikel = apiInterface.getPengaduan(
                sharedPrefManager.getSpAppToken(),
                sharedPrefManager.getSpProdesaToken(),
                sharedPrefManager.getSpDesaCode()
        );
        getArtikel.enqueue(new Callback<PengaduanResponse>() {
            @Override
            public void onResponse(Call<PengaduanResponse> call, Response<PengaduanResponse> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    pengaduanLiveData.postValue(response.body().getPengaduanList().getPengaduans());
                    Log.d("csdvsv", String.valueOf(response.body().getPengaduanList().getPengaduans() != null));
                    for (Pengaduan pengaduan: response.body().getPengaduanList().getPengaduans()){
//                        artikel.getJudul();
                        Log.d("description", pengaduan.getDescription());
                    }

                }
            }

            @Override
            public void onFailure(Call<PengaduanResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<ArrayList<Pengaduan>> getPengaduanResponseLiveData() {
        return pengaduanLiveData;
    }
}
