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
import com.example.pro_desa.adapter.AdapterListPengaduan;
import com.example.pro_desa.model.Pengaduan;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.repository.PengaduanRepository;
import com.example.pro_desa.ui.user.HomeUserActivity;
import com.example.pro_desa.utils.SharedPrefManager;
import com.example.pro_desa.viewmodels.PengaduanViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.example.pro_desa.Myapp.getContext;

public class PengaduanActivity extends AppCompatActivity implements View.OnClickListener {

    private AdapterListPengaduan adapterListPengaduan;
    public SharedPrefManager sharedPrefManager;
    public ApiInterface apiInterface;
    Context context;

    PengaduanViewModel pengaduanViewModel;
    PengaduanRepository pengaduanRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaduan);

        adapterListPengaduan = new AdapterListPengaduan(getContext());

        sharedPrefManager = new SharedPrefManager(getContext());

        pengaduanRepository = new PengaduanRepository();

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refresh_ly);
        LinearLayout linearLayout = findViewById(R.id.ln_kosong_pengaduan);
        RecyclerView recyclerView  = findViewById(R.id.rv_pengaduan);
        ImageView img_btn_back = findViewById(R.id.img_btn_back);
        img_btn_back.setOnClickListener(this);

        SearchView searchView = findViewById(R.id.search_view);
        searchView.onActionViewExpanded(); //new Added line
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Cari pengaduan");
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
                adapterListPengaduan.getFilter().filter(newText);
                return false;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterListPengaduan);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        pengaduanViewModel = ViewModelProviders.of(this).get(PengaduanViewModel.class);
        pengaduanViewModel.init();
        pengaduanViewModel.getPengaduanLiveData().observe(this, new Observer<ArrayList<Pengaduan>>() {
            @Override
            public void onChanged(ArrayList<Pengaduan> pengaduans) {
                adapterListPengaduan.setPengaduan(pengaduans);

                Log.d("asdcadscd", String.valueOf(adapterListPengaduan.getItemCount()));
                if (adapterListPengaduan.getItemCount() != 0){
                    linearLayout.setVisibility(View.INVISIBLE);
                    searchView.setVisibility(View.VISIBLE);
                } else {
                    linearLayout.setVisibility(View.VISIBLE);
                    searchView.setVisibility(View.INVISIBLE);
                }
            }
        });

//        RelativeLayout rv_art_list  = findViewById(R.id.rv_art_list);
        FloatingActionButton fl_add = findViewById(R.id.fab_add);
//        rv_art_list.setOnClickListener(this);
        fl_add.setOnClickListener(this);

        reloadPengaduanList();
    }

    @Override
    public void onResume(){
        super.onResume();
        pengaduanViewModel.getPengaduan();
    }

    public void reloadPengaduanList() {
        pengaduanViewModel.getPengaduan();
        pengaduanRepository.getPengaduan();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_btn_back:
                startActivity(new Intent(PengaduanActivity.this, HomeUserActivity.class));
                break;

            case R.id.fab_add:
                Intent fab_add  = new Intent(this, FormPengaduanActivity.class);
                startActivity(fab_add);
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}