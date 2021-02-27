package com.example.pro_desa.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.model.Bantuan;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.network.response.BantuanResponse;
import com.example.pro_desa.utils.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BantuanRepository {
    private MutableLiveData<ArrayList<Bantuan>> bantuanLiveData;
    private ApiInterface apiInterface;
    private SharedPrefManager sharedPrefManager;

    public BantuanRepository() {
        sharedPrefManager = new SharedPrefManager(Myapp.getContext());
        bantuanLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getBantuan(){
        Call<BantuanResponse> getBantuan = apiInterface.getBantuan(
                sharedPrefManager.getSpAppToken(),
                sharedPrefManager.getSpProdesaToken(),
                sharedPrefManager.getSpDesaCode()
        );
        getBantuan.enqueue(new Callback<BantuanResponse>() {
            @Override
            public void onResponse(Call<BantuanResponse> call, Response<BantuanResponse> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    bantuanLiveData.postValue(response.body().getBantuanList().getBantuans());
                    Log.d("isissssisiis", String.valueOf(response.body().getBantuanList().getBantuans() != null));
                    for (Bantuan bantuan: response.body().getBantuanList().getBantuans()){
//                        artikel.getJudul();
//                        Log.d("judul", bantuan.getJudul());
                    }

                }
            }

            @Override
            public void onFailure(Call<BantuanResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<ArrayList<Bantuan>> getBantuanResponseLiveData() {
        return bantuanLiveData;
    }
}
