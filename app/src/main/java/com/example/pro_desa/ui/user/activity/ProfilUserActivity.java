package com.example.pro_desa.ui.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
import android.widget.Toast;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.R;
import com.example.pro_desa.model.User;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.network.response.BaseResponse;
import com.example.pro_desa.utils.FileUtils;
import com.example.pro_desa.utils.SharedPrefManager;
import com.example.pro_desa.viewmodels.ProfileViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.pro_desa.Myapp.getContext;

public class ProfilUserActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;
    Context mContext;
    ProgressDialog progressDialog;

    ProfileViewModel profileViewModel;

    TextInputEditText txt_nik, txt_nama, txt_email;
    CircularImageView img_profil;
    ImageView btn_updte_profil;
    Button btn_edit_profil;

    Uri uri;
    File file;

    private static final int PICK_IMAGE = 1;
    private static final int PERMISSION_REQUEST_STORAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_user);

        mContext = this;

        sharedPrefManager = new SharedPrefManager(getContext());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        txt_nik     = findViewById(R.id.ip_nik_pl);
        txt_nama    = findViewById(R.id.ip_nama_pl);
        txt_email   = findViewById(R.id.ip_email_pl);
        img_profil = findViewById(R.id.img_profil);
        btn_updte_profil = findViewById(R.id.btn_upadte_img_profil);
        btn_edit_profil = findViewById(R.id.btn_edit_profil);

        btn_updte_profil.setOnClickListener(this);
        btn_edit_profil.setOnClickListener(this);

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.init();
        profileViewModel.getProfileResponseLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User response) {
                if (response != null){
                    sharedPrefManager.setSpNama(response.getFullname());
                    txt_nik.setText(sharedPrefManager.getSpNik());
                    txt_nama.setText(sharedPrefManager.getSpNama());
                    txt_email.setText(sharedPrefManager.getSpEmail());

                    Log.d("responses", response.getNik());
                }
            }
        });

        txt_nik.setText(sharedPrefManager.getSpNik());
        txt_nama.setText(sharedPrefManager.getSpNama());
        txt_email.setText(sharedPrefManager.getSpEmail());

        Picasso.with(getContext())
                .load(sharedPrefManager.getSpAvatar())
                .fit().centerCrop()
                .error(R.drawable.user_no)
                .into(img_profil);

    }

    @Override
    public void onResume(){
        super.onResume();
        profileViewModel.getProfile();
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
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            if(data != null) {
                uri = data.getData();
                img_profil.setImageURI(uri);
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                uri = result.getUri();
                ((ImageView) findViewById(R.id.img_profil)).setImageURI(uri);


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
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (uri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(uri);
        } else {
            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Start crop image activity for the given image.
     */
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .setMultiTouchEnabled(true)
                .start(this);

        Log.d("image_uri", String.valueOf(imageUri));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_upadte_img_profil:
                choosePhoto();
                break;

            case R.id.btn_edit_profil:
                progressDialog.show();
                MultipartBody.Part filename = null;

                if (uri != null) {
                    file = new File(uri.getPath());
                    try {
                        file = FileUtils.from(ProfilUserActivity.this, uri);
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

                    Call<BaseResponse> updateProfil = apiInterface.updateProfil(
                            filename
                    );

                    Log.d("log_update", updateProfil.toString());
                    updateProfil.enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                            progressDialog.dismiss();
                            if (response.code() >= 200 && response.code() < 300){
                                String message = response.body().getMessage();
                                Log.d("pesan", message);

                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

                                finish();
                            } else {
//                                try {
//                                    Gson gson = new Gson();
//                                    BaseResponse errorResponse = gson.fromJson(
//                                            response.errorBody().string(),
//                                            BaseResponse.class);
//
//                                    for (Map.Entry<String, ArrayList<String>> entry : errorResponse.getErrors().entrySet()) {
//                                        String key = entry.getKey();
//                                        String value = entry.getValue().get(0);
//                                        Toast.makeText(mContext, value, Toast.LENGTH_SHORT).show();
//                                    }
//                                } catch (Exception e){
//
//                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {
                            progressDialog.dismiss();
                        }
                    });

                    break;

                }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}