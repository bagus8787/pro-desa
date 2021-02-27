package com.example.pro_desa.repository;

import android.icu.lang.UCharacter;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.model.AppData;
import com.example.pro_desa.model.User;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.network.response.BaseResponse;
import com.example.pro_desa.network.response.UserResponse;
import com.example.pro_desa.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {
    private MutableLiveData<User> profileLiveData;
    private ApiInterface apiInterface;
    private SharedPrefManager sharedPrefManager;

    public ProfileRepository() {
        sharedPrefManager = new SharedPrefManager(Myapp.getContext());
        profileLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getProfile() {

        Call<UserResponse> getAppToken = apiInterface.getUser(
                sharedPrefManager.getSpAppToken(),
                sharedPrefManager.getSpProdesaToken(),
                sharedPrefManager.getSpDesaCode(),
                sharedPrefManager.getSpNik()
        );
//        Call<User> getFcm = apiInterface.getUser(sharedPrefManager.getSpFcm());

        getAppToken.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    profileLiveData.postValue(response.body().getUser());

//                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_APP_TOKEN, response.body().getToken() != null);
//
//                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_FCM, response.body().getFcm_key() != null);
//
//                    Log.d("HasWarga", String.valueOf(response.body().getToken() != null));
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

//        Call<AppData> getUser = apiInterface.getUser(sharedPrefManager.getSpAppToken());
    }


    public LiveData<User> getProfileResponseLiveData() {
        return profileLiveData;
    }

//    public LiveData<BarcodeImage> getBarcodeResponseLiveData() {
//        return barcodeLiveData;
//    }
}
