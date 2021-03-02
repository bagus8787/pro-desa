package com.example.pro_desa.ui.user.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pro_desa.R;

import org.apache.commons.lang3.StringUtils;

public class DetailPengaduanActivity extends AppCompatActivity {

    int IT_ID;
    String IT_JUDUL, IT_ISI, IT_TGL_UPLOAD, IT_DETAIL_LOKASI, IT_DESC, IT_KATEGORI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengaduan);

        IT_ID = getIntent().getIntExtra("IT_ID", 0);
        IT_JUDUL = getIntent().getStringExtra("IT_JUDUL");
//        IT_ISI = getIntent().getStringExtra("IT_ISI");
        IT_TGL_UPLOAD = getIntent().getStringExtra("IT_TGL_UPLOAD");
        IT_DESC = getIntent().getStringExtra("IT_DESC");
        IT_DETAIL_LOKASI = getIntent().getStringExtra("IT_DETAIL_LOKASI");
        IT_KATEGORI = getIntent().getStringExtra("IT_KATEGORI");

        TextView judul      = findViewById(R.id.txt_title_pe);
        TextView tgl_upload = findViewById(R.id.txt_tgl_upload);
        TextView desc       = findViewById(R.id.txt_desc_pe);
        TextView kategori   = findViewById(R.id.txt_kat_pe);
        TextView detail_lokasi  =   findViewById(R.id.txt_detail_lokasi);
        ImageView img_btn_back  = findViewById(R.id.img_btn_back);
        img_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailPengaduanActivity.this, PengaduanActivity.class));
                finish();
            }
        });

        judul.setText(StringUtils.capitalize(IT_JUDUL.toLowerCase().trim()));
        tgl_upload.setText(IT_TGL_UPLOAD);
        desc.setText(StringUtils.capitalize(IT_DESC.toLowerCase().trim()));
        kategori.setText(IT_KATEGORI);

        if (IT_DETAIL_LOKASI != null){
            detail_lokasi.setText(StringUtils.capitalize(IT_DETAIL_LOKASI.toLowerCase().trim()));
        } else {
            detail_lokasi.setText("Detail lokasi belum ada");
        }


        Log.d("pengCCDSCDSCSDad", IT_JUDUL);
    }
}