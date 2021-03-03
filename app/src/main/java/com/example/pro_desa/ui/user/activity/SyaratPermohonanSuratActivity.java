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
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pro_desa.R;
import com.example.pro_desa.adapter.AdapterListPermohonanSurat;
import com.example.pro_desa.adapter.AdapterSyaratPermohonanSurat;
import com.example.pro_desa.model.PermohonanSuratList;
import com.example.pro_desa.model.SyaratPermohonanSuratList;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.repository.PermohonanSuratRepository;
import com.example.pro_desa.repository.SyaratPermohonanSuratRepository;
import com.example.pro_desa.utils.SharedPrefManager;
import com.example.pro_desa.viewmodels.PermohonanSuratViewModel;
import com.example.pro_desa.viewmodels.SyaratPermohonanSuratViewModel;
import com.theartofdev.edmodo.cropper.CropImage;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.pro_desa.Myapp.getContext;

public class SyaratPermohonanSuratActivity extends AppCompatActivity implements View.OnClickListener {

    private AdapterSyaratPermohonanSurat adapterSyaratPermohonanSurat;
    public SharedPrefManager sharedPrefManager;
    public ApiInterface apiInterface;
    Context context;

    ArrayList<SyaratPermohonanSuratList> syaratPermohonanSuratLists;

    SyaratPermohonanSuratViewModel syaratPermohonanSuratViewModel;
    SyaratPermohonanSuratRepository syaratPermohonanSuratRepository;

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;

    TextView txt_nama_permohonan;
    String it_nama_permohonan;
    ImageView img_btn_back;

    public static final int PICK_IMAGE = 1;
    public static final int PERMISSION_REQUEST_STORAGE = 2;

    File file;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syarat_permohonan_surat);

        setResult(RESULT_OK);

        it_nama_permohonan = getIntent().getStringExtra("IT_NAMA");

        adapterSyaratPermohonanSurat = new AdapterSyaratPermohonanSurat(getContext());

        sharedPrefManager = new SharedPrefManager(getContext());

        syaratPermohonanSuratRepository = new SyaratPermohonanSuratRepository();

        swipeRefreshLayout = findViewById(R.id.refresh_ly);
        recyclerView = findViewById(R.id.rv_syarat_perm_surat_list);
        txt_nama_permohonan = findViewById(R.id.txt_nama_permohonan);
        img_btn_back = findViewById(R.id.img_btn_back);
        img_btn_back.setOnClickListener(this);

        txt_nama_permohonan.setText(it_nama_permohonan);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterSyaratPermohonanSurat);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        syaratPermohonanSuratViewModel = ViewModelProviders.of(this).get(SyaratPermohonanSuratViewModel.class);
        syaratPermohonanSuratViewModel.init();
        syaratPermohonanSuratViewModel.getSyaratPermohonanSuratLiveData().observe(this, new Observer<ArrayList<SyaratPermohonanSuratList>>() {
            @Override
            public void onChanged(ArrayList<SyaratPermohonanSuratList> permohonanSuratLists) {
                adapterSyaratPermohonanSurat.setPermohonanSuratLists(permohonanSuratLists);
                Log.d("syarataaaa", String.valueOf(adapterSyaratPermohonanSurat.getItemCount()));
                if (adapterSyaratPermohonanSurat.getItemCount() != 0) {
                } else {
                }
            }
        });

//        RelativeLayout rv_art_list  = findViewById(R.id.rv_art_list);
//        rv_art_list.setOnClickListener(this);
        reloadPermohonanSuratList();
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