package com.example.pro_desa.ui.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.R;
import com.example.pro_desa.model.ListKategoriPengaduan;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.network.response.BaseResponse;
import com.example.pro_desa.network.response.kategori_response.KategoriPengaduanResponse;
import com.example.pro_desa.utils.FileUtils;
import com.example.pro_desa.utils.SharedPrefManager;
import com.google.android.material.textfield.TextInputEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormPengaduanActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;
    Context mContext;
    ProgressDialog progressDialog;

    ArrayAdapter<ListKategoriPengaduan> listKategoriPengaduanArrayAdapter;

    SearchableSpinner searchablePengaduanSpinner;
    String id_kat;
    TextInputEditText ip_judul, ip_deskripsi, ip_lokasi;
    ImageView ip_img;
    Button btn_add_pengajuan;

    private static final int PICK_IMAGE = 1;
    private static final int PERMISSION_REQUEST_STORAGE = 2;

    File file;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pengaduan);

        sharedPrefManager = new SharedPrefManager(Myapp.getContext());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        mContext = this;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        ip_judul    = findViewById(R.id.ip_judul);
        ip_deskripsi= findViewById(R.id.ip_deskripsi);
        ip_lokasi   = findViewById(R.id.ip_lokasi);
        ip_img      = findViewById(R.id.ip_img);
        btn_add_pengajuan = findViewById(R.id.btn_add_pengajuan);

        ip_img.setOnClickListener(this);
        btn_add_pengajuan.setOnClickListener(this);

        searchablePengaduanSpinner = findViewById(R.id.sp_name);

        searchablePengaduanSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorTextInput));
                String nama = parent.getItemAtPosition(position).toString();
                id_kat = String.valueOf(listKategoriPengaduanArrayAdapter.getItem(position).getId());
                Log.d("id_kat", id_kat);
                Log.d("nama", nama);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getKategoriPengaduan();

    }

    public void getKategoriPengaduan(){
        Call<KategoriPengaduanResponse> kategoriPengaduanResponseCall = apiInterface.getKategoriPengaduan(
                sharedPrefManager.getSpAppToken(),
                sharedPrefManager.getSpProdesaToken(),
                sharedPrefManager.getSpDesaCode());
        kategoriPengaduanResponseCall.enqueue(new Callback<KategoriPengaduanResponse>() {
            @Override
            public void onResponse(Call<KategoriPengaduanResponse> call, Response<KategoriPengaduanResponse> response) {
                listKategoriPengaduanArrayAdapter = new ArrayAdapter<ListKategoriPengaduan>(FormPengaduanActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, response.body().getListKategoriPengaduans());
                listKategoriPengaduanArrayAdapter.setDropDownViewResource(R.layout.list_item_region);
                searchablePengaduanSpinner.setTitle("Pilih Kategori");
                searchablePengaduanSpinner.setAdapter(listKategoriPengaduanArrayAdapter);
            }

            @Override
            public void onFailure(Call<KategoriPengaduanResponse> call, Throwable t) {

            }
        });
    }

    private void choosePhoto() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
//            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            if(data != null) {
                uri = data.getData();
                ip_img.setImageURI(uri);
//                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                uri = result.getUri();
                ((ImageView) findViewById(R.id.ip_img)).setImageURI(uri);


                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "cropped" , "cropped");

                } catch (IOException e) {
                    e.printStackTrace();
                }

//                Log.d("result", String.valueOf(uri));

                Toast.makeText(this, "Gambar disimpan", Toast.LENGTH_LONG).show();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Toast.makeText(this, "Gambar gagal di crop: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_pengajuan:
                if (ip_judul.getText().toString().isEmpty()){
                    ip_judul.setError("Judul harus diisi");
                    ip_judul.requestFocus();
                    return;
                }
                if (ip_deskripsi.getText().toString().isEmpty()){
                    ip_deskripsi.setError("Deskripsi harus diisi");
                    ip_deskripsi.requestFocus();
                    return;
                }
                if (ip_lokasi.getText().toString().isEmpty()){
                    ip_lokasi.setError("Lokasi harus diisi");
                    ip_lokasi.requestFocus();
                    return;
                }
                progressDialog.show();

                MultipartBody.Part filename = null;
                if (uri != null) {
                    file = new File(uri.getPath());
                    try {
                        file = FileUtils.from(FormPengaduanActivity.this, uri);
                        Log.d("file", "File...:::: uti - " + file.getPath() + " file -" + file + " : " + file.exists());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    MediaType media;
                    if (uri.toString().startsWith("content://")) {
                        media = MediaType.parse(getContentResolver().getType(uri));
                    } else {
                        media = MediaType.parse(uri.toString());
                    }
                    RequestBody requestFile =
                            RequestBody.create(
                                    media,
                                    file
                            );

                    filename = MultipartBody.Part.createFormData("foto_pendukung", file.getName(), requestFile);
                }

                RequestBody getNik = RequestBody.create(MediaType.parse("text/plain"), sharedPrefManager.getSpNik().toString());
                RequestBody idKat = RequestBody.create(MediaType.parse("text/plain"), id_kat.toString());
                RequestBody judul = RequestBody.create(MediaType.parse("text/plain"), ip_judul.getText().toString());
                RequestBody deskripsi = RequestBody.create(MediaType.parse("text/plain"), ip_deskripsi.getText().toString());
                RequestBody lokasi = RequestBody.create(MediaType.parse("text/plain"), ip_lokasi.getText().toString());

                Call<BaseResponse> addPengaduan = apiInterface.addPengaduan(
                        sharedPrefManager.getSpAppToken(),
                        sharedPrefManager.getSpProdesaToken(),
                        sharedPrefManager.getSpDesaCode(),
                        judul,
                        deskripsi,
                        filename,
                        idKat,
                        getNik
                );

                Log.d("Log_update", addPengaduan.toString());
                addPengaduan.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        progressDialog.dismiss();
                        if(response.body().getCode() >= 200 && response.body().getCode() < 300){
                            Log.d("response_add", response.body().getMessage());
                        } else {
                            Log.d("response_add", response.body().getMessage());
                        }

                        Toast.makeText(FormPengaduanActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        progressDialog.dismiss();

                    }
                });
                break;

            case R.id.ip_img:
                choosePhoto();
                break;


        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}