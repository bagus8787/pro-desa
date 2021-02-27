package com.example.pro_desa.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pro_desa.R;
import com.example.pro_desa.utils.TextValidator;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText fm_nik, fm_nama, fm_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fm_nik = findViewById(R.id.fm_nik);
        fm_nama = findViewById(R.id.fm_namel);
        fm_email = findViewById(R.id.fm_email);

        fm_nik.addTextChangedListener(new TextValidator(fm_nik) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.length() == 5){
                    textView.setError("NIK harus diisi 16 digit");
                }
            }
        });

        Button btn_lanjut   = findViewById(R.id.btn_lanjut);
        btn_lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fm_nik.getText().toString().isEmpty()){
                    fm_nik.setError("NIK harus diisi");
                    fm_nik.addTextChangedListener(new TextValidator(fm_nik) {
                        @Override
                        public void validate(TextView textView, String text) {
                            if (text.length() <= 5){
                                textView.setError("NIK harus diisi 16 digit");
                            }
                        }
                    });
                    fm_nik.requestFocus();
                    return;
                }
                if (fm_nama.getText().toString().isEmpty()){
                    fm_nama.setError("Nama harus diisi");
                    fm_nama.requestFocus();
                    return;
                }
                if (fm_email.getText().toString().isEmpty()){
                    fm_email.setError("Email harus diisi");
                    fm_email.requestFocus();
                    return;
                }

                Intent l    = new Intent(RegisterActivity.this, Register2Activity.class);
                l.putExtra("fm_nik", fm_nik.getText().toString());
                l.putExtra("fm_nama", fm_nama.getText().toString());
                l.putExtra("fm_email", fm_email.getText().toString());
                startActivity(l);
            }
        });

//        getSupportActionBar().hide();
    }
}