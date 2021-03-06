package com.example.pro_desa.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pro_desa.R;
import com.example.pro_desa.model.AppData;
import com.example.pro_desa.model.Role;
import com.example.pro_desa.model.User;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.network.response.BaseResponse;
import com.example.pro_desa.network.response.ResponseDataAwal;
import com.example.pro_desa.network.response.UserResponse;
import com.example.pro_desa.ui.user.HomeUserActivity;
import com.example.pro_desa.utils.SharedPrefManager;
import com.example.pro_desa.utils.TextValidator;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;
    ProgressDialog progressDialog;

    EditText nik_lg, pass_lg;

    TextView txt_btn_daftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;

        ButterKnife.bind(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sharedPrefManager = new SharedPrefManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        nik_lg = findViewById(R.id.fm_nik_lg);
        pass_lg = findViewById(R.id.fm_pass_lg);
//        txt_btn_daftar
        txt_btn_daftar = findViewById(R.id.txt_btn_daftar);
        txt_btn_daftar.setOnClickListener(this);

        nik_lg.addTextChangedListener(new TextValidator(nik_lg) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.length() == 0){
                    textView.setError("NIK harus diisi");
                }
            }
        });

        pass_lg.addTextChangedListener(new TextValidator(pass_lg) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.length() == 0){
                    textView.setError("Password harus diisi");
                }
            }
        });

        Button btn_login    = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                if (nik_lg.getText().toString().isEmpty()){
                    nik_lg.setError("NIK harus diisi");
                    nik_lg.requestFocus();
                    return;
                }
                if (pass_lg.getText().toString().isEmpty()){
                    pass_lg.setError("Password harus diisi");
                    pass_lg.requestFocus();
                    return;
                }
                progressDialog.show();
                Call<ResponseDataAwal> postLogin = apiInterface.postLogin(
                        nik_lg.getText().toString(),
                        pass_lg.getText().toString());
                postLogin.enqueue(new Callback<ResponseDataAwal>() {
                    @Override
                    public void onResponse(Call<ResponseDataAwal> call, Response<ResponseDataAwal> response) {
                        progressDialog.dismiss();
                        Log.d("cek_message", response.body().getMessage());

                        if (response.body().getResponseData() != null) {
                            User user = response.body().getResponseData().getUserData();
//                            Role user_role = response.body().getResponseData().getUserData().getRole();
                            AppData appData = response.body().getResponseData().getAppData();

                            Log.d("token", response.body().getResponseData().getUserData().getToken());
//                            Log.d("fcm", String.valueOf(response.body().getResponseData().getUserData().getFcm_key() != null));
                            Log.d("appKey", response.body().getResponseData().getAppData().getApp_key());
                            Log.d("appDesaCode", response.body().getResponseData().getAppData().getApp_desa_code());

//                            Log.d("toleName", response.body().getResponseData().getUserData().getRole_name());

                            sharedPrefManager.saveSPString(SharedPrefManager.SP_ID, String.valueOf(user.getId()));
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_NIK, user.getNik());
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, user.getFullname());
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, user.getEmail());
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_LOGIN_WEB, user.getLogin_url_web());

                            if (user.getFoto() == null){
                                sharedPrefManager.saveSPString(SharedPrefManager.SP_AVATAR, "no_pict");
                            } else {
                                sharedPrefManager.saveSPString(SharedPrefManager.SP_AVATAR, user.getFoto());
                            }

//                            Log.d("foto", response.body().getResponseData().getUserData().getFoto());

//                            sharedPrefManager.saveSPString(SharedPrefManager.SP_ROLE, user.getRole_name());

                            Log.d("roleu", user.getRole_name());

                            if (user.getFcm_key() == null){
                                sharedPrefManager.saveSPString(SharedPrefManager.SP_FCM, "");
                            } else {
                                sharedPrefManager.saveSPString(SharedPrefManager.SP_FCM, user.getFcm_key());
                            }
//                            sharedPrefManager.saveSPString(SharedPrefManager.SP_FCM, user.getFcm_key());
                            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);

                            sharedPrefManager.saveSPString(SharedPrefManager.SP_PRODESA_TOKEN, user.getToken());
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_APP_TOKEN, appData.getApp_key());
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_DESA_CODE, appData.getApp_desa_code());

                            if (!sharedPrefManager.getSPSudahLogin()) {
                                Intent login = new Intent(LoginActivity.this, HomeUserActivity.class);
                                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(login);
                                finish();
                            }

                            Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(LoginActivity.this, HomeUserActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();

                        } else {

                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseDataAwal> call, Throwable t) {
                        progressDialog.dismiss();

                        Toast.makeText(LoginActivity.this, "Email atau password salah", Toast.LENGTH_SHORT).show();
                    }
                });

                break;

            case R.id.txt_btn_daftar:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}