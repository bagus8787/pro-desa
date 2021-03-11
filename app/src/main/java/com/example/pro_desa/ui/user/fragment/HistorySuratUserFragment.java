package com.example.pro_desa.ui.user.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.R;
import com.example.pro_desa.adapter.AdapterListPermohonanSurat;
import com.example.pro_desa.model.PermohonanSurat;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.repository.PermohonanSuratRepository;
import com.example.pro_desa.utils.SharedPrefManager;
import com.example.pro_desa.viewmodels.PermohonanSuratViewModel;

import java.util.ArrayList;

public class HistorySuratUserFragment extends Fragment {
    private AdapterListPermohonanSurat adapterListPermohonanSurat;
    public SharedPrefManager sharedPrefManager;
    public ApiInterface apiInterface;
    Context context;

    PermohonanSuratViewModel permohonanSuratViewModel;
    PermohonanSuratRepository permohonanSuratRepository;
    LinearLayout linearLayout;
    SearchView searchView;

    public HistorySuratUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_history_surat_user,
                container, false);

        permohonanSuratRepository = new PermohonanSuratRepository();

        SwipeRefreshLayout swipeRefreshLayout = rootView.findViewById(R.id.refresh_ly);
        linearLayout = rootView.findViewById(R.id.ln_kosong_permohonan_surat);
        RecyclerView recyclerView  = rootView.findViewById(R.id.rv_permohonan_surat);

        searchView = rootView.findViewById(R.id.search_view);
        searchView.onActionViewExpanded(); //new Added line
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Cari permohoan Surat");

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
                reloadPengumumanList();
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstance){
        super.onCreate(savedInstance);
        adapterListPermohonanSurat = new AdapterListPermohonanSurat(getContext());

        sharedPrefManager = new SharedPrefManager(Myapp.getContext());

        permohonanSuratViewModel = ViewModelProviders.of(this).get(PermohonanSuratViewModel.class);
        permohonanSuratViewModel.init();
        permohonanSuratViewModel.getPermohonanSuratLiveData().observe(this, new Observer<ArrayList<PermohonanSurat>>() {
            @Override
            public void onChanged(ArrayList<PermohonanSurat> permohonanSurats) {
                adapterListPermohonanSurat.setPermohonanSuratLists(permohonanSurats);

                Log.d("xafcdafsd", String.valueOf(adapterListPermohonanSurat.getItemCount()));
                if (adapterListPermohonanSurat.getItemCount() != 0){
                    linearLayout.setVisibility(View.INVISIBLE);
                    searchView.setVisibility(View.VISIBLE);

                } else {
                    linearLayout.setVisibility(View.VISIBLE);
                    searchView.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reloadPengumumanList();

    }

    @Override
    public void onResume(){
        super.onResume();
        permohonanSuratViewModel.getPermohonanSurat();
    }

    public void reloadPengumumanList() {
        permohonanSuratViewModel.getPermohonanSurat();
        permohonanSuratRepository.getPermohonanSurat();
    }
}