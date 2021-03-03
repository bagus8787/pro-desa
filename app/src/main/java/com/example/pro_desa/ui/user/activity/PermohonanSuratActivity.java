package com.example.pro_desa.ui.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.pro_desa.R;
import com.example.pro_desa.adapter.AdapterListPermohonanSurat;
import com.example.pro_desa.model.PermohonanSuratList;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.repository.PermohonanSuratRepository;
import com.example.pro_desa.ui.user.HomeUserActivity;
import com.example.pro_desa.utils.SharedPrefManager;
import com.example.pro_desa.viewmodels.PermohonanSuratViewModel;

import java.util.ArrayList;

import static com.example.pro_desa.Myapp.getContext;

public class PermohonanSuratActivity extends AppCompatActivity {
    private AdapterListPermohonanSurat adapterListPermohonanSurat;
    public SharedPrefManager sharedPrefManager;
    public ApiInterface apiInterface;
    Context context;

    ArrayList<PermohonanSuratList> permohonanSuratLists;

    PermohonanSuratViewModel permohonanSuratViewModel;
    PermohonanSuratRepository permohonanSuratRepository;

    LinearLayout linearLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    SearchView searchView;
    ImageView img_btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permohonan_surat);

        adapterListPermohonanSurat = new AdapterListPermohonanSurat(getContext());

        sharedPrefManager = new SharedPrefManager(getContext());

        permohonanSuratRepository = new PermohonanSuratRepository();

        swipeRefreshLayout = findViewById(R.id.refresh_ly);
        linearLayout = findViewById(R.id.ln_kosong_art);
        recyclerView  = findViewById(R.id.rv_perm_surat_list);
        img_btn_back = findViewById(R.id.img_btn_back);
        img_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PermohonanSuratActivity.this, HomeUserActivity.class));
                finish();
            }
        });

        searchView = findViewById(R.id.search_view);
        searchView.onActionViewExpanded(); //new Added line
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Cari permohonan surat");
//        searchView.setBackgroundResource(R.drawable.background_spinner);

        if(!searchView.isFocused()) {
            searchView.clearFocus();
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapterListPermohonanSurat.getFilter().filter(newText);
                return false;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterListPermohonanSurat);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        permohonanSuratViewModel = ViewModelProviders.of(this).get(PermohonanSuratViewModel.class);
        permohonanSuratViewModel.init();
        permohonanSuratViewModel.getPermohonanSuratLiveData().observe(this, new Observer<ArrayList<PermohonanSuratList>>() {
            @Override
            public void onChanged(ArrayList<PermohonanSuratList> permohonanSuratLists) {
                adapterListPermohonanSurat.setPermohonanSuratLists(permohonanSuratLists);

                Log.d("asdcadscd", String.valueOf(adapterListPermohonanSurat.getItemCount()));
                if (adapterListPermohonanSurat.getItemCount() != 0){
                    linearLayout.setVisibility(View.INVISIBLE);
                    searchView.setVisibility(View.VISIBLE);
                } else {
                    linearLayout.setVisibility(View.VISIBLE);
                    searchView.setVisibility(View.INVISIBLE);
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
        permohonanSuratViewModel.getPermohonanSurat();
    }

    public void reloadPermohonanSuratList() {
        permohonanSuratViewModel.getPermohonanSurat();
        permohonanSuratRepository.getPermohonanSurat();
    }
}