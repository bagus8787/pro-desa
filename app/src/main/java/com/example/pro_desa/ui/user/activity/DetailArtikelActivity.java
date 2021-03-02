package com.example.pro_desa.ui.user.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pro_desa.R;
import com.example.pro_desa.model.Artikel;
import com.example.pro_desa.ui.user.HomeUserActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailArtikelActivity extends AppCompatActivity {
    int IT_ID;
    String IT_JUDUL, IT_ISI, IT_TGL_UPLOAD, IT_URL_GAMBAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artikel);

        IT_ID = getIntent().getIntExtra("IT_ID", 0);
        IT_JUDUL = getIntent().getStringExtra("IT_JUDUL");
        IT_ISI = getIntent().getStringExtra("IT_ISI");
        IT_TGL_UPLOAD = getIntent().getStringExtra("IT_TGL_UPLOAD");
        IT_URL_GAMBAR = getIntent().getStringExtra("IT_URL_GAMBAR");

        TextView judul  = findViewById(R.id.txt_judul_artikel);
        TextView tgl_upload = findViewById(R.id.txt_tgl_upload_artikel);
        ImageView img_gambar = findViewById(R.id.img_detail_artikel);
        ImageView img_btn_back = findViewById(R.id.img_btn_back);
        img_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(DetailArtikelActivity.this, Artikel.class));
                onBackPressed();
            }
        });

        Picasso.with(getApplicationContext())
                .load(IT_URL_GAMBAR)
                .error(R.drawable.bg_null)
                .into(img_gambar);

        //Isi
        WebView view = findViewById(R.id.txt_isi_artikel);
        view.setBackgroundColor(Color.TRANSPARENT);
        String text;
        text = "<html><body><p align=\"justify\">";
        text = IT_ISI;
        text+= "</p></body></html>";
        view.loadData(text, "text/html", "utf-8");

        judul.setText(IT_JUDUL);
//        isi.setText(IT_ISI);
        tgl_upload.setText(IT_TGL_UPLOAD);

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}