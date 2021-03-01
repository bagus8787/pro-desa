package com.example.pro_desa.ui.user.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.R;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.network.response.BaseResponse;
import com.example.pro_desa.utils.SharedPrefManager;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;
    ProgressDialog progressDialog;

    TextInputEditText ip_pass, ip_pass_conf;
    Button btn_reset_pass;
    ImageView img_btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        sharedPrefManager = new SharedPrefManager(Myapp.getContext());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        ip_pass         = findViewById(R.id.ip_reset_pass);
        ip_pass_conf    = findViewById(R.id.ip_reset_conf_pass);
        btn_reset_pass = findViewById(R.id.btn_reset_pass);
        img_btn_back    = findViewById(R.id.img_btn_back);

        btn_reset_pass.setOnClickListener(this);
        img_btn_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_btn_back:
                startActivity(new Intent(ResetPasswordActivity.this, ProfilActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;

            case R.id.btn_reset_pass:
//                Log.d("pass", String.valueOf(ip_pass_conf));

                if (ip_pass_conf.getText().toString().equals(ip_pass.getText().toString())){
                    progressDialog.show();

                    Call<BaseResponse> resetResponse = apiInterface.resetPassword(
                            sharedPrefManager.getSpAppToken(),
                            sharedPrefManager.getSpProdesaToken(),
                            sharedPrefManager.getSpNik(),
                            ip_pass_conf.getText().toString());
                    resetResponse.enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                            progressDialog.dismiss();
                            Log.d("repsonse reset", "aaa" + response.body().getMessage());

                            Toast.makeText(ResetPasswordActivity.this, "Password berhasil di ubah", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(ResetPasswordActivity.this, ProfilActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {
                            progressDialog.dismiss();
                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ResetPasswordActivity.this, "Password harus sama", Toast.LENGTH_LONG).show();
                }

                break;

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}