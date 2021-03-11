package com.example.pro_desa.ui.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pro_desa.R;
import com.example.pro_desa.adapter.AdapterSyaratPermohonanSurat;
import com.example.pro_desa.model.ListFile;
import com.example.pro_desa.model.SyaratPermohonanSuratList;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.network.response.BaseResponse;
import com.example.pro_desa.network.response.file_response.PermohonanFileResponse;
import com.example.pro_desa.repository.SyaratPermohonanSuratRepository;
import com.example.pro_desa.utils.SharedPrefManager;
import com.example.pro_desa.viewmodels.SyaratPermohonanSuratViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.pro_desa.Myapp.getContext;

public class SyaratPermohonanSuratActivity extends AppCompatActivity implements View.OnClickListener {

    private AdapterSyaratPermohonanSurat adapterSyaratPermohonanSurat;
    public SharedPrefManager sharedPrefManager;
    public ApiInterface apiInterface;
    Context context;
    ProgressDialog progressDialog;

    ArrayList<ListFile> listFiles;
    ArrayList<SyaratPermohonanSuratList> syaratPermohonanSuratLists;

    ListFile listFile;
    Map<String, String> dataSyarat = new HashMap<>();
    JSONObject dataSyaratJson;

    SyaratPermohonanSuratViewModel syaratPermohonanSuratViewModel;
    SyaratPermohonanSuratRepository syaratPermohonanSuratRepository;

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;

    TextView txt_nama_permohonan;
    String it_nama_permohonan, it_id_syarat, it_url_gambar_file, ip_syarat;
    TextInputEditText ip_peruntukan, ip_nomor_wa;
    ImageView img_btn_back;
    Button btn_submit_pe_surat;

    public static final int PICK_IMAGE = 1;
    public static final int PERMISSION_REQUEST_STORAGE = 2;

    File file;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syarat_permohonan_surat);

        it_nama_permohonan = getIntent().getStringExtra("IT_NAMA");
        it_id_syarat = getIntent().getStringExtra("IT_ID");

//        Log.d("it_id_syarat", "=" + it_nama_permohonan);

        sharedPrefManager = new SharedPrefManager(getContext());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        syaratPermohonanSuratRepository = new SyaratPermohonanSuratRepository();

        swipeRefreshLayout = findViewById(R.id.refresh_ly);
        recyclerView = findViewById(R.id.rv_syarat_perm_surat_list);

        txt_nama_permohonan = findViewById(R.id.txt_nama_permohonan);

        ip_peruntukan   = findViewById(R.id.ip_peruntukan);
        ip_nomor_wa     = findViewById(R.id.ip_nomor_wa);

        img_btn_back = findViewById(R.id.img_btn_back);
        btn_submit_pe_surat = findViewById(R.id.btn_submit_pe_surat);

        btn_submit_pe_surat.setOnClickListener(this);
        img_btn_back.setOnClickListener(this);

        txt_nama_permohonan.setText(it_nama_permohonan);

        adapterSyaratPermohonanSurat = new AdapterSyaratPermohonanSurat(getContext());
        recyclerView.setAdapter(adapterSyaratPermohonanSurat);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                reloadPermohonanSuratList();

            }
        });

        Call<PermohonanFileResponse> fileResponseCall = apiInterface.getFile(
                sharedPrefManager.getSpAppToken(),
                sharedPrefManager.getSpProdesaToken(),
                sharedPrefManager.getSpDesaCode(),
                sharedPrefManager.getSpNik()
        );

        fileResponseCall.enqueue(new Callback<PermohonanFileResponse>() {
            @Override
            public void onResponse(Call<PermohonanFileResponse> call, Response<PermohonanFileResponse> response) {
                response.body().getFileResponse().getListFiles();
                adapterSyaratPermohonanSurat.SetFiles(response.body().getFileResponse().getListFiles());
                if (adapterSyaratPermohonanSurat.getItemCount() != 0) {
                } else {
                }
            }

            @Override
            public void onFailure(Call<PermohonanFileResponse> call, Throwable t) {

            }
        });

        syaratPermohonanSuratViewModel = ViewModelProviders.of(this).get(SyaratPermohonanSuratViewModel.class);
        syaratPermohonanSuratViewModel.init();
        syaratPermohonanSuratViewModel.getSyaratPermohonanSuratLiveData().observe(this, new Observer<ArrayList<SyaratPermohonanSuratList>>() {
            @Override
            public void onChanged(ArrayList<SyaratPermohonanSuratList> permohonanSuratLists) {
                adapterSyaratPermohonanSurat.setPermohonanSuratLists(permohonanSuratLists);

                Call<PermohonanFileResponse> j = apiInterface.getFile(
                        sharedPrefManager.getSpAppToken(),
                        sharedPrefManager.getSpProdesaToken(),
                        sharedPrefManager.getSpDesaCode(),
                        sharedPrefManager.getSpNik()
                );

                j.enqueue(new Callback<PermohonanFileResponse>() {
                    @Override
                    public void onResponse(Call<PermohonanFileResponse> call, Response<PermohonanFileResponse> response) {

                        listFiles = response.body().getFileResponse().getListFiles();
                        for (SyaratPermohonanSuratList syaratPermohonanSuratList: permohonanSuratLists){
                            ListFile tfile = new ListFile(syaratPermohonanSuratList.getRef_syarat_id());
                            if (listFiles.contains(tfile)){
                                int idx = listFiles.indexOf(tfile);
                                dataSyarat.put(String.valueOf(syaratPermohonanSuratList.getRef_syarat_id()) , String.valueOf(listFiles.get(idx).getId()));
//                                Log.d("contains", String.valueOf(syaratPermohonanSuratList.getRef_syarat_id()) + " - "+ String.valueOf(idx) + " -- " + listFiles.get(idx).toString());

                            } else {
                                // validasi
                                // return;
                                // hapus dataSyaratJson
                                // hapus dataSyarat

//                                Toast.makeText(SyaratPermohonanSuratActivity.this, "Ada file persyaratan yang belum legkap, silahkan lengkapi terlebih dahulu" ,Toast.LENGTH_LONG).show();

//                                return;
                            }

                        }

                        dataSyaratJson = new JSONObject(dataSyarat);
                        Log.d("json", dataSyaratJson.toString());

                    }

                    @Override
                    public void onFailure(Call<PermohonanFileResponse> call, Throwable t) {

                    }
                });


            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                reloadPermohonanSuratList();
            }
        }, 1000);

//        reloadPermohonanSuratList();
    }

    @Override
    public void onResume(){
        super.onResume();
        syaratPermohonanSuratViewModel.getSyaratPermohonanSurat();
    }

    public void reloadPermohonanSuratList() {
        syaratPermohonanSuratViewModel.getSyaratPermohonanSurat();
        syaratPermohonanSuratRepository.getSyaratPermohonanSurat();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_btn_back:
                startActivity(new Intent(SyaratPermohonanSuratActivity.this, PermohonanSuratActivity.class));
                break;

            case R.id.btn_submit_pe_surat:
                if (ip_peruntukan.getText().toString().isEmpty()){
                    ip_peruntukan.setError("Peruntukan harus diisi");
                    ip_peruntukan.requestFocus();
                    return;
                }

                if (ip_nomor_wa.getText().toString().isEmpty()){
                    ip_nomor_wa.setError("Nomor harus diisi");
                    ip_nomor_wa.requestFocus();
                    return;
                }

                Log.d("dataJson", dataSyaratJson.toString());

                progressDialog.show();

                Call<BaseResponse> addPermohonanSurat = apiInterface.addPermohonanSurat(
                        sharedPrefManager.getSpAppToken(),
                        sharedPrefManager.getSpProdesaToken(),
                        sharedPrefManager.getSpDesaCode(),
                        sharedPrefManager.getSpNik(),
                        it_id_syarat,
                        ip_peruntukan.getText().toString(),
                        ip_nomor_wa.getText().toString(),
                        dataSyaratJson.toString(),
                        it_nama_permohonan + " " + sharedPrefManager.getSpNik()
                        );

                addPermohonanSurat.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        progressDialog.dismiss();

                        if (response.body().getCode() >= 200 && response.body().getCode() < 300){
                            Log.d("log_Add", response.body().getMessage());
                            Toast.makeText(SyaratPermohonanSuratActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                        } else {
                            Log.d("log_Add", response.body().getMessage());
                            Toast.makeText(SyaratPermohonanSuratActivity.this,"Ada dokumen yang belum lengkap, Silahkan lengkapi terlebih dahulu", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(SyaratPermohonanSuratActivity.this,"Ada dokumen yang belum lengkap, Silahkan lengkapi terlebih dahulu", Toast.LENGTH_LONG).show();
                    }
                });

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void choosePhoto(int id) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_STORAGE);

        }else{
            openGallery();
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
//            start(context.startActivity(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
//        super.onCreateViewHolder(requestCode, requestCode, data)
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
//            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            if (data != null) {
                uri = data.getData();
//                    ip_img.setImageURI(uri);
//                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                uri = result.getUri();
//                    ((ImageView) mView.findViewById(R.id.ip_img)).setImageURI(uri);

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                    MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "cropped", "cropped");

                } catch (IOException e) {
                    e.printStackTrace();
                }

//                Log.d("result", String.valueOf(uri));

                Toast.makeText(context, "Gambar disimpan", Toast.LENGTH_LONG).show();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Toast.makeText(this, "Gambar gagal di crop: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }

    }
}