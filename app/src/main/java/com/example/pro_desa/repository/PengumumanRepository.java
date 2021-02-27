package com.example.pro_desa.repository;

import android.util.Log;
import android.widget.LinearLayout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.model.Pengumuman;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.network.response.PengumumanResponse;
import com.example.pro_desa.utils.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengumumanRepository {
    private MutableLiveData<ArrayList<Pengumuman>> pengumumanLiveData;
    private ApiInterface apiInterface;
    private SharedPrefManager sharedPrefManager;

    public PengumumanRepository() {
        sharedPrefManager = new SharedPrefManager(Myapp.getContext());
        pengumumanLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getPengumuman(){
        Call<PengumumanResponse> getPengumuman = apiInterface.getPengumuman(
                sharedPrefManager.getSpAppToken(),
                sharedPrefManager.getSpProdesaToken(),
                sharedPrefManager.getSpDesaCode(),
                sharedPrefManager.getSpNik()
        );
        getPengumuman.enqueue(new Callback<PengumumanResponse>() {
            @Override
            public void onResponse(Call<PengumumanResponse> call, Response<PengumumanResponse> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    pengumumanLiveData.postValue(response.body().getPengumumanList());
                    Log.d("csdcsdcd", String.valueOf(response.body().getPengumumanList() != null));
                    for (Pengumuman pengumuman: response.body().getPengumumanList()){
//                        pengumuman.getJudul();
                        Log.d("judulpee", pengumuman.getJudul_tautan());
                    }

                }
            }

            @Override
            public void onFailure(Call<PengumumanResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<ArrayList<Pengumuman>> getPengumumanResponseLiveData() {
        return pengumumanLiveData;
    }
}
