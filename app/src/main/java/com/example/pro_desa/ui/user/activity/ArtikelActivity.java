package com.example.pro_desa.ui.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.example.pro_desa.R;
import com.example.pro_desa.adapter.AdapterListArtikelVertical;
import com.example.pro_desa.adapter.AdapterListPengaduan;
import com.example.pro_desa.model.Artikel;
import com.example.pro_desa.model.Pengaduan;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.repository.ArtikelRepository;
import com.example.pro_desa.repository.PengaduanRepository;
import com.example.pro_desa.ui.user.HomeUserActivity;
import com.example.pro_desa.utils.SharedPrefManager;
import com.example.pro_desa.viewmodels.ArtikelViewModel;
import com.example.pro_desa.viewmodels.PengaduanViewModel;

import java.util.ArrayList;

import static com.example.pro_desa.Myapp.getContext;

public class ArtikelActivity extends AppCompatActivity implements View.OnClickListener {

    private AdapterListArtikelVertical adapterListArtikelVertical;
    public SharedPrefManager sharedPrefManager;
    public ApiInterface apiInterface;
    Context context;

    ArrayList<Artikel> artikels;

    ArtikelViewModel artikelViewModel;
    ArtikelRepository artikelRepository;

    LinearLayout linearLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    SearchView searchView;
    ImageView img_btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikel);

        adapterListArtikelVertical = new AdapterListArtikelVertical(getContext());

        sharedPrefManager = new SharedPrefManager(getContext());

        artikelRepository = new ArtikelRepository();

        swipeRefreshLayout = findViewById(R.id.refresh_ly);
        linearLayout = findViewById(R.id.ln_kosong_art);
        recyclerView  = findViewById(R.id.rv_art_list);
        img_btn_back = findViewById(R.id.img_btn_back);
        img_btn_back.setOnClickListener(this);

        searchView = findViewById(R.id.search_view);
        searchView.onActionViewExpanded(); //new Added line
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Cari artikel");
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
                adapterListArtikelVertical.getFilter().filter(newText);
                return false;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterListArtikelVertical);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                reloadArtikelList();

            }
        });

        artikelViewModel = ViewModelProviders.of(this).get(ArtikelViewModel.class);
        artikelViewModel.init();
        artikelViewModel.getArtikelLiveData().observe(this, new Observer<ArrayList<Artikel>>() {
            @Override
            public void onChanged(ArrayList<Artikel> artikels) {
                adapterListArtikelVertical.setArtikels(artikels);

                Log.d("asdcadscd", String.valueOf(adapterListArtikelVertical.getItemCount()));
                if (adapterListArtikelVertical.getItemCount() != 0){
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
        reloadArtikelList();
    }

    @Override
    public void onResume(){
        super.onResume();
        artikelViewModel.getArtikel();
    }

    public void reloadArtikelList() {
        artikelViewModel.getArtikel();
        artikelRepository.getArtikel();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_btn_back:
                Intent rv_art_list  = new Intent(this, HomeUserActivity.class);
                startActivity(rv_art_list);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}