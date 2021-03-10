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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.R;
import com.example.pro_desa.model.ListFile;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.network.response.BaseResponse;
import com.example.pro_desa.network.response.file_response.FileResponse;
import com.example.pro_desa.network.response.file_response.PermohonanFileResponse;
import com.example.pro_desa.utils.FileUtils;
import com.example.pro_desa.utils.SharedPrefManager;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PilihFileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE = 1;
    private static final int PERMISSION_REQUEST_STORAGE = 2;

    File file;
    Uri uri;

    String IT_ID, IT_NAMA, IT_URL_GAMBAR, IT_URL_GAMBAR_NAMA;
    TextView txt_nama_syarat, txt_nama_file;
    ImageView img_file, img_btn_back;
    Button btn_upload_syarat, btn_pilih_file;

    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;
    ProgressDialog progressDialog;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_file);

        sharedPrefManager = new SharedPrefManager(Myapp.getContext());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        mContext = this;

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        IT_ID = getIntent().getStringExtra("IT_REF_SYARAT_ID");
        IT_NAMA = getIntent().getStringExtra("IT_NAMA");
        IT_URL_GAMBAR = getIntent().getStringExtra("IT_URL_GAMBAR");
        IT_URL_GAMBAR_NAMA = getIntent().getStringExtra("IT_URL_GAMBAR_NAMA");

        Log.d("id_syaratref", IT_ID);
//        Log.d("IT_URL_GAMBAR", IT_URL_GAMBAR);

        txt_nama_syarat = findViewById(R.id.txt_nama_syarat);
        txt_nama_file = findViewById(R.id.txt_nama_file);
        img_file    = findViewById(R.id.img_file);
        btn_upload_syarat = findViewById(R.id.btn_upload_syarat);
        img_btn_back = findViewById(R.id.img_btn_back);

        txt_nama_syarat.setText(IT_NAMA);

        btn_upload_syarat.setOnClickListener(this);
        img_btn_back.setOnClickListener(this);

        if (IT_URL_GAMBAR != null){
            Picasso.with(getApplicationContext())
                    .load(IT_URL_GAMBAR)
                    .resize(160,160)
                    .error(R.drawable.bg_null)
                    .into(img_file);

            txt_nama_file.setText(IT_URL_GAMBAR_NAMA);
            btn_upload_syarat.setText("Perbarui File");

        } else {

        }

//        cek_data();
    }

    public void cek_data(){
        Call<PermohonanFileResponse> fileResponseCall = apiInterface.getFile(
                sharedPrefManager.getSpAppToken(),
                sharedPrefManager.getSpProdesaToken(),
                sharedPrefManager.getSpDesaCode(),
                sharedPrefManager.getSpNik()
        );

        fileResponseCall.enqueue(new Callback<PermohonanFileResponse>() {
            @Override
            public void onResponse(Call<PermohonanFileResponse> call, Response<PermohonanFileResponse> response) {
//                Log.d("response file", String.valueOf(response.body().getFileResponse().getListFiles()));
                for (ListFile listFile: response.body().getFileResponse().getListFiles()){
                    listFile.getSatuan();

                    String id_syarat = String.valueOf(listFile.getId_syarat());
                    String gambar = "http://222.124.168.221:8500/demo/prodesa-putat/desa/upload/dokumen/" + String.valueOf(listFile.getSatuan());

                    Log.d("id_syarat2", id_syarat);
                    Log.d("gambar", gambar);

                    if (IT_ID.equals(id_syarat)){
                        Picasso.with(getApplicationContext())
                                .load(gambar)
                                .resize(160,160)
                                .error(R.drawable.bg_null)
                                .into(img_file);

                        txt_nama_file.setText(listFile.getNama());
                        btn_upload_syarat.setText("Perbarui File");

                    } else {
//                        img_file.setImageResource(R.drawable.bg_null);
//                        Picasso.with(getApplicationContext())
//                                .load(gambar)
//                                .error(R.drawable.bg_null)
//                                .into(img_file);
                    }
                }
            }

            @Override
            public void onFailure(Call<PermohonanFileResponse> call, Throwable t) {

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

                img_file.setImageURI(uri);
                img_file.setScaleType(ImageView.ScaleType.FIT_XY);

                txt_nama_file.setText(IT_NAMA + "_" + sharedPrefManager.getSpNik() + ".jpg");


//                startCropImageActivity(imageUri);
            }
            upload_foto();
        }

    }

    public void upload_foto(){

        MultipartBody.Part filename = null;
        if (uri != null) {
            file = new File(uri.getPath());
            try {
                file = FileUtils.from(PilihFileActivity.this, uri);
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

            filename = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        }

        RequestBody nama = RequestBody.create(MediaType.parse("text/plain"), sharedPrefManager.getSpNik()+"_"+file.getName());
        RequestBody id_syarat_surat = RequestBody.create(MediaType.parse("text/plain"), IT_ID);

        Log.d("nama_file_upload", String.valueOf(nama));

        progressDialog.show();

        Call<BaseResponse> addLetter = apiInterface.addLetter(
                sharedPrefManager.getSpAppToken(),
                sharedPrefManager.getSpProdesaToken(),
                sharedPrefManager.getSpDesaCode(),
                sharedPrefManager.getSpNik(),
                id_syarat_surat,
                nama,
                filename
        );

        Log.d("addLetter", String.valueOf(addLetter));

        addLetter.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                progressDialog.dismiss();

                if (response.body().getCode() >= 200 && response.body().getCode() < 300){
                    Log.d("response", response.body().getMessage());

                    Toast.makeText(PilihFileActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

//                    finish();
                } else {
                    Log.d("response", response.body().getMessage());
                    img_file.setImageResource(R.drawable.bg_null);
                    txt_nama_file.setText(" ");

                    Toast.makeText(PilihFileActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
//                    finish();

                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_upload_syarat:
                choosePhoto();
                break;

            case R.id.img_btn_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}