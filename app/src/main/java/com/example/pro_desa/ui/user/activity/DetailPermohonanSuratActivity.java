package com.example.pro_desa.ui.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.R;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.network.response.BaseResponse;
import com.example.pro_desa.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.pro_desa.Myapp.getContext;

public class DetailPermohonanSuratActivity extends AppCompatActivity implements View.OnClickListener {
    String IT_STATUS, IT_NAMA, IT_CREATED, IT_ID;
    TextView txt_diajukan, txt_belum, txt_menunggu, txt_siap, txt_sudah, txt_created, txt_keterangan_surat;
    ImageView ic_diajukan, ic_belum, ic_menunggu, ic_siap, ic_sudah;
    Button btn_batal_permohonan;

    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;
    ProgressDialog progressDialog;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_permohonan_surat);

        IT_ID = getIntent().getStringExtra("IT_ID");
        IT_NAMA = getIntent().getStringExtra("IT_NAMA");
        IT_STATUS = getIntent().getStringExtra("IT_STATUS");
        IT_CREATED = getIntent().getStringExtra("IT_CREATED");

        sharedPrefManager = new SharedPrefManager(getContext());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        mContext = this;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        ic_diajukan = findViewById(R.id.ic_diajukan);
        ic_belum = findViewById(R.id.ic_belumdiperiksa);
        ic_menunggu = findViewById(R.id.ic_menunggu);
        ic_siap = findViewById(R.id.ic_siap);
        ic_sudah = findViewById(R.id.ic_sudah);

        txt_diajukan = findViewById(R.id.txt_diajukan);
        txt_belum = findViewById(R.id.txt_belum);
        txt_menunggu = findViewById(R.id.txt_menunggu);
        txt_siap = findViewById(R.id.txt_siap);
        txt_sudah = findViewById(R.id.txt_sudah);

        txt_keterangan_surat = findViewById(R.id.txt_keterangan_surat);
        txt_created = findViewById(R.id.txt_tgl_pe_surat);

        txt_keterangan_surat.setText(IT_NAMA);
        txt_created.setText(IT_CREATED);

        btn_batal_permohonan = findViewById(R.id.btn_batal_permohonan);
        btn_batal_permohonan.setOnClickListener(this);

        if (IT_STATUS.equals("0")){
            status_diajukan();
        } else if (IT_STATUS.equals("2")){
            status_menunggu();

        } else if (IT_STATUS.equals("3")){
            status_siap();

        }else if (IT_STATUS.equals("4")){
            status_sudah();

        }else if (IT_STATUS.equals("9")){
            dibatalkan();
        }

    }

    public void status_diajukan(){
        ic_diajukan.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
    }

    public void status_menunggu(){
        ic_diajukan.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        ic_belum.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
    }

    public void status_siap(){
        ic_diajukan.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        ic_belum.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        ic_menunggu.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
    }

    public void status_sudah(){
        ic_diajukan.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));

        ic_belum.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        ic_menunggu.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        ic_siap.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));

        btn_batal_permohonan.setVisibility(View.INVISIBLE);
    }

    public void status_belum(){

    }

    public void dibatalkan(){
        txt_belum.setText("Dibatalkan");

        ic_diajukan.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));

        ic_belum.setVisibility(View.INVISIBLE);
        ic_menunggu.setVisibility(View.INVISIBLE);
        ic_siap.setVisibility(View.INVISIBLE);
        ic_sudah.setVisibility(View.INVISIBLE);

        txt_menunggu.setVisibility(View.INVISIBLE);
        txt_siap.setVisibility(View.INVISIBLE);
        txt_sudah.setVisibility(View.INVISIBLE);

        btn_batal_permohonan.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_batal_permohonan:
                progressDialog.show();
                String alasan_pembatalan;
                alasan_pembatalan = " ";

                Call<BaseResponse> cancelPermohonan = apiInterface.cancelPermohonanSurat(
                        sharedPrefManager.getSpAppToken(),
                        sharedPrefManager.getSpProdesaToken(),
                        sharedPrefManager.getSpDesaCode(),
                        sharedPrefManager.getSpNik(),
                        IT_ID,
                        alasan_pembatalan
                );

                cancelPermohonan.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        progressDialog.dismiss();
                        Toast.makeText(DetailPermohonanSuratActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        onBackPressed();

                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}