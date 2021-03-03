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
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.network.response.BaseResponse;
import com.example.pro_desa.utils.FileUtils;
import com.example.pro_desa.utils.SharedPrefManager;
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

    String IT_ID, IT_NAMA;
    TextView txt_nama_syarat, txt_nama_file;
    ImageView img_file;
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

        Log.d("id_syaratref", IT_ID);

        txt_nama_syarat = findViewById(R.id.txt_nama_syarat);
        txt_nama_file = findViewById(R.id.txt_nama_file);
        img_file    = findViewById(R.id.img_file);
        btn_pilih_file = findViewById(R.id.btn_pilih_file);
        btn_upload_syarat = findViewById(R.id.btn_upload_syarat);

        txt_nama_syarat.setText(IT_NAMA);

        btn_pilih_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });

        btn_upload_syarat.setOnClickListener(this);

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
        }

        // handle result of CropImageActivity

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_upload_syarat:
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

                RequestBody nama = RequestBody.create(MediaType.parse("text/plain"), IT_NAMA + "_" + sharedPrefManager.getSpNik() + ".jpg");
                RequestBody id_syarat_surat = RequestBody.create(MediaType.parse("text/plain"), IT_ID);

                Log.d("nama_file_upload", String.valueOf(nama));

                Call<BaseResponse> addLetter = apiInterface.addLetter(
                        sharedPrefManager.getSpAppToken(),
                        sharedPrefManager.getSpProdesaToken(),
                        sharedPrefManager.getSpDesaCode(),
                        sharedPrefManager.getSpNik(),
                        id_syarat_surat,
                        nama,
                        filename
                );

                addLetter.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        Log.d("response", response.body().getMessage());
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {

                    }
                });
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}