package com.example.pro_desa.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.R;
import com.example.pro_desa.model.Artikel;
import com.example.pro_desa.model.region.Desa;
import com.example.pro_desa.model.region.Kabupaten;
import com.example.pro_desa.model.region.Kecamatan;
import com.example.pro_desa.model.region.Provinsi;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.network.response.RegisterResponse;
import com.example.pro_desa.network.response.ResponseDataAwal;
import com.example.pro_desa.network.response.region_response.DesaResponse;
import com.example.pro_desa.network.response.region_response.KabupatenResponse;
import com.example.pro_desa.network.response.region_response.KecamatanResponse;
import com.example.pro_desa.network.response.region_response.ProvinsiResponse;
import com.example.pro_desa.utils.SharedPrefManager;
import com.google.android.material.textfield.TextInputEditText;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register2Activity extends AppCompatActivity {

    String fm_nik, fm_nama, fm_email;
    Button btn_register;
    ArrayAdapter<Provinsi> provinsiArrayAdapter;
    ArrayAdapter<Kabupaten> kabupatenArrayAdapter;
    ArrayAdapter<Kecamatan> kecamatanArrayAdapter;
    ArrayAdapter<Desa> desaArrayAdapter;

    TextInputEditText fm_pass, fm_pass_conf;

    SearchableSpinner searchableSpinnerProv, searchableSpinnerKab, searchableSpinnerKec, searchableSpinnerDesa;

    Context mContext;
    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;
    ProgressDialog progressDialog;

    String id_prov,id_kab, id_kec, id_desa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        sharedPrefManager = new SharedPrefManager(Myapp.getContext());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        mContext = this;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        fm_nik = getIntent().getStringExtra("fm_nik");
        fm_nama = getIntent().getStringExtra("fm_nama");
        fm_email = getIntent().getStringExtra("fm_email");

        searchableSpinnerProv = findViewById(R.id.fm_prov);
        searchableSpinnerKab = findViewById(R.id.fm_kab);
        searchableSpinnerKec = findViewById(R.id.fm_kec);
        searchableSpinnerDesa = findViewById(R.id.fm_desa);

//        setProv
        searchableSpinnerProv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPengumuman));

                String nama = parent.getItemAtPosition(position).toString();

                if (id_prov == null){
                    id_prov = provinsiArrayAdapter.getItem(position).getId_prov();
//                    getKab();
                    Log.d("id_prov", id_prov);
                    Log.d("nama", nama);
                } else {
                    id_prov = provinsiArrayAdapter.getItem(position).getId_prov();
                    getKab();
                    Log.d("id_prov", id_prov);
                    Log.d("nama", nama);
                }
//                getKab();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        setKab
        searchableSpinnerKab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPengumuman));
                String nama = parent.getItemAtPosition(position).toString();
                id_kab = kabupatenArrayAdapter.getItem(position).getId_kab();
                Log.d("id_kab", id_kab);
                Log.d("nama", nama);

                getKec();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        setKec
        searchableSpinnerKec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPengumuman));
                String nama = parent.getItemAtPosition(position).toString();
                id_kec = kecamatanArrayAdapter.getItem(position).getId_kec();
                Log.d("id_kec", id_kec);
                Log.d("nama", nama);

                getDesa();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        setDesa
        searchableSpinnerDesa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPengumuman));
                String nama = parent.getItemAtPosition(position).toString();
                id_desa = desaArrayAdapter.getItem(position).getId_kel();
                Log.d("id_desa", id_desa);
                Log.d("nama", nama);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fm_pass = findViewById(R.id.fm_pass);
        fm_pass_conf = findViewById(R.id.fm_pass_conf);

        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fm_pass.getText().toString().isEmpty()){
                    fm_pass.setError("Password harus diisi");
                    fm_pass.requestFocus();
                    return;
                }
                progressDialog.show();
                Call<RegisterResponse> postRegister = apiInterface.postRegister(
                        fm_nik,
                        fm_nama,
                        fm_email,
                        fm_pass.getText().toString(),
                        Integer.parseInt(id_prov),
                        Integer.parseInt(id_kab),
                        Integer.parseInt(id_kec),
                        Long.parseLong(id_desa),
                        Integer.parseInt(id_prov),
                        Integer.parseInt(id_kab),
                        Integer.parseInt(id_kec),
                        Long.parseLong(id_desa));
                postRegister.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        progressDialog.dismiss();

                        if (response.body().getCode() >= 200 && response.body().getCode() < 300){
                            Log.d("daftar", response.body().getMessage());
                            Toast.makeText(Register2Activity.this,response.body().getMessage(), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Register2Activity.this, LoginActivity.class).
                                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();

                        } else {
                            Toast.makeText(Register2Activity.this,response.body().getMessage(), Toast.LENGTH_LONG).show();
                            Toast.makeText(Register2Activity.this, "Registrasi gagal", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        progressDialog.dismiss();

                    }
                });

            }
        });

        getProvisi();
    }

    public void getProvisi(){
        Call<ProvinsiResponse> provinsiResponseCall = apiInterface.getProv();
        provinsiResponseCall.enqueue(new Callback<ProvinsiResponse>() {
            @Override
            public void onResponse(Call<ProvinsiResponse> call, Response<ProvinsiResponse> response) {
                provinsiArrayAdapter = new ArrayAdapter<Provinsi>(Register2Activity.this, android.R.layout.simple_spinner_dropdown_item, response.body().getProvinsis());
                provinsiArrayAdapter.setDropDownViewResource(R.layout.list_item_region);
                searchableSpinnerProv.setTitle("Pilih Provinsi");
                searchableSpinnerProv.setAdapter(provinsiArrayAdapter);
            }

            @Override
            public void onFailure(Call<ProvinsiResponse> call, Throwable t) {

            }
        });
    }

    public void getKab(){
        Call<KabupatenResponse> kabupatenResponseCall = apiInterface.getKab(id_prov);
        kabupatenResponseCall.enqueue(new Callback<KabupatenResponse>() {
            @Override
            public void onResponse(Call<KabupatenResponse> call, Response<KabupatenResponse> response) {
                kabupatenArrayAdapter = new ArrayAdapter<Kabupaten>(Register2Activity.this, android.R.layout.simple_spinner_dropdown_item, response.body().getKabupatens());
                kabupatenArrayAdapter.setDropDownViewResource(R.layout.list_item_region);
                searchableSpinnerKab.setTitle("Pilih Kabupaten");
                searchableSpinnerKab.setAdapter(kabupatenArrayAdapter);
            }

            @Override
            public void onFailure(Call<KabupatenResponse> call, Throwable t) {

            }
        });
    }

    public void getKec(){
        Call<KecamatanResponse> kecamatanResponseCall = apiInterface.getKec(id_kab);
        kecamatanResponseCall.enqueue(new Callback<KecamatanResponse>() {
            @Override
            public void onResponse(Call<KecamatanResponse> call, Response<KecamatanResponse> response) {
                kecamatanArrayAdapter = new ArrayAdapter<Kecamatan>(Register2Activity.this, android.R.layout.simple_spinner_dropdown_item, response.body().getKecamatans());
                kecamatanArrayAdapter.setDropDownViewResource(R.layout.list_item_region);
                searchableSpinnerKec.setTitle("Pilih Kecamatan");
                searchableSpinnerKec.setAdapter(kecamatanArrayAdapter);
            }

            @Override
            public void onFailure(Call<KecamatanResponse> call, Throwable t) {

            }
        });
    }

    public void getDesa(){
        Call<DesaResponse> desaResponseCall = apiInterface.getDesa(id_kec);
        desaResponseCall.enqueue(new Callback<DesaResponse>() {
            @Override
            public void onResponse(Call<DesaResponse> call, Response<DesaResponse> response) {
                desaArrayAdapter = new ArrayAdapter<Desa>(Register2Activity.this, android.R.layout.simple_spinner_dropdown_item, response.body().getDesas());
                desaArrayAdapter.setDropDownViewResource(R.layout.list_item_region);
                searchableSpinnerDesa.setTitle("Pilih Desa");
                searchableSpinnerDesa.setAdapter(desaArrayAdapter);
            }

            @Override
            public void onFailure(Call<DesaResponse> call, Throwable t) {

            }
        });
    }
}