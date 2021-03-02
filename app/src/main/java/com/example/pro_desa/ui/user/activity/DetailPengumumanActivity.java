package com.example.pro_desa.ui.user.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.pro_desa.R;

import org.apache.commons.lang3.StringUtils;

public class DetailPengumumanActivity extends AppCompatActivity {

    int IT_ID;
    String IT_JUDUL, IT_ISI, IT_TGL_UPLOAD, IT_TGL_MULAI, IT_TGL_BERAKHIR, IT_DARI;
    TextView judul, isi, tgl_upload, tgl_mulai,tgl_berakhir, dari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengumuman);

        IT_ID = getIntent().getIntExtra("IT_ID", 0);
        IT_JUDUL = getIntent().getStringExtra("IT_JUDUL");
        IT_ISI = getIntent().getStringExtra("IT_ISI");
        IT_TGL_UPLOAD = getIntent().getStringExtra("IT_TGL_UPLOAD");
        IT_TGL_MULAI = getIntent().getStringExtra("IT_TGL_MULAI");
        IT_TGL_BERAKHIR = getIntent().getStringExtra("IT_TGL_BERAKHIR");
        IT_DARI = getIntent().getStringExtra("IT_DARI");

        judul   = findViewById(R.id.txt_judul_pe);
        isi     = findViewById(R.id.txt_isi_pe);
        tgl_upload  = findViewById(R.id.txt_tgl_dibuat_pe);
        tgl_mulai = findViewById(R.id.txt_tgl_mulai_pe);
        tgl_berakhir = findViewById(R.id.txt_tgl_selesai_pe);

        judul.setText(StringUtils.capitalize(IT_JUDUL.toLowerCase().trim()));
        isi.setText(StringUtils.capitalize(IT_ISI.toLowerCase().trim()));
        tgl_upload.setText(IT_TGL_UPLOAD);
        tgl_mulai.setText(IT_TGL_MULAI);
        tgl_berakhir.setText(IT_TGL_BERAKHIR);

    }
}