package com.example.pro_desa.ui.user.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pro_desa.R;

public class DetailPermohonanSuratActivity extends AppCompatActivity {
    String IT_STATUS, IT_NAMA, IT_CREATED;
    TextView txt_diajukan, txt_belum, txt_menunggu, txt_siap, txt_sudah;
    ImageView ic_diajukan, ic_belum, ic_menunggu, ic_siap, ic_sudah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_permohonan_surat);

        IT_NAMA = getIntent().getStringExtra("IT_NAMA");
        IT_STATUS = getIntent().getStringExtra("IT_STATUS");
        IT_CREATED = getIntent().getStringExtra("IT_CREATED");

        ic_diajukan = findViewById(R.id.ic_diajukan);
        ic_belum = findViewById(R.id.ic_belumdiperiksa);
        ic_menunggu = findViewById(R.id.ic_menunggu);
        ic_siap = findViewById(R.id.ic_siap);
        ic_sudah = findViewById(R.id.ic_sudah);

        txt_diajukan = findViewById(R.id.txt_diajukan);
        txt_belum = findViewById(R.id.txt_belum);
        txt_menunggu = findViewById(R.id.txt_menunggu);
        txt_siap = findViewById(R.id.txt_siap);
        txt_sudah = findViewById(R.id.txt_sudah);

        if (IT_STATUS.equals("0")){
            status_diajukan();
        } else if (IT_STATUS.equals("1")){

        }

    }

    public void status_diajukan(){
        ic_belum.setVisibility(View.INVISIBLE);
        ic_menunggu.setVisibility(View.INVISIBLE);
        ic_siap.setVisibility(View.INVISIBLE);
        ic_sudah.setVisibility(View.INVISIBLE);

        txt_menunggu.setVisibility(View.INVISIBLE);
        txt_siap.setVisibility(View.INVISIBLE);
        txt_sudah.setVisibility(View.INVISIBLE);

    }

    public void status_belum(){

    }

    public void status_menunggu(){

    }

    public void status_siap(){

    }

    public void status_sudah(){

    }

}