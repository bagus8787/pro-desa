package com.example.pro_desa.ui.user.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.R;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.network.response.BaseResponse;
import com.example.pro_desa.network.response.LogOutResponse;
import com.example.pro_desa.ui.activity.LoginActivity;
import com.example.pro_desa.ui.user.HomeUserActivity;
import com.example.pro_desa.utils.SharedPrefManager;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.pro_desa.Myapp.getContext;

public class ProfilActivity extends AppCompatActivity implements View.OnClickListener {

    public SharedPrefManager sharedPrefManager;
    public ApiInterface apiInterface;
    Context mContext;
    ProgressDialog progressDialog;
    String loginWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        mContext = this;

        sharedPrefManager = new SharedPrefManager(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        TextView nama_pl    = findViewById(R.id.txt_nama_pl);
        TextView email_pl   = findViewById(R.id.txt_email_pl);
        TextView role_pl    = findViewById(R.id.txt_role_pl);
        ImageView img_btn_back = findViewById(R.id.img_btn_back);
        Button btn_edit_profil = findViewById(R.id.btn_edit_profil);

        RelativeLayout rv_logout    = findViewById(R.id.rv_logout);
        RelativeLayout rv_login_Web = findViewById(R.id.rv_login_web);
        RelativeLayout rv_reset_pass= findViewById(R.id.rv_reset_pass);

        CircularImageView img_profil = findViewById(R.id.img_profil);

        nama_pl.setText(StringUtils.capitalize(sharedPrefManager.getSpNama().toLowerCase().trim()));
        email_pl.setText(sharedPrefManager.getSpEmail());
        role_pl.setText(StringUtils.capitalize(sharedPrefManager.getSpRole().toLowerCase().trim()));
        loginWeb = sharedPrefManager.getSpLoginWeb();

        Log.d("fotou", sharedPrefManager.getSpAvatar());
        Picasso.with(getContext())
                .load(sharedPrefManager.getSpAvatar())
                .fit().centerCrop()
                .error(R.drawable.user_no)
                .into(img_profil);
//        nama_pl.setText(sharedPrefManager.getSpNama());

        rv_logout.setOnClickListener(this);
        rv_login_Web.setOnClickListener(this);
        rv_reset_pass.setOnClickListener(this);

        img_btn_back.setOnClickListener(this);
        btn_edit_profil.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rv_logout:
                progressDialog.show();
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_PRODESA_TOKEN, "");
                sharedPrefManager.saveSPString(SharedPrefManager.SP_APP_TOKEN, "");

                Call<LogOutResponse> logout = apiInterface.log_out(Integer.parseInt(sharedPrefManager.getSpId()));
                logout.enqueue(new Callback<LogOutResponse>() {
                    @Override
                    public void onResponse(Call<LogOutResponse> call, Response<LogOutResponse> response) {
                        progressDialog.dismiss();
                        if (response.code() >= 200 && response.code() < 300 && response.body() != null) {
                            Toast.makeText(ProfilActivity.this, "Log Out berhasil",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ProfilActivity.this, LoginActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();

                        }
                    }

                    @Override
                    public void onFailure(Call<LogOutResponse> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });

                break;

            case R.id.rv_login_web:
                progressDialog.show();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(loginWeb));
                startActivity(i);
                progressDialog.dismiss();
                break;

            case R.id.btn_edit_profil:
                startActivity(new Intent(ProfilActivity.this, ProfilUserActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;

            case R.id.rv_reset_pass:
                startActivity(new Intent(ProfilActivity.this, ResetPasswordActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;

            case R.id.img_btn_back:
                startActivity(new Intent(ProfilActivity.this, HomeUserActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}