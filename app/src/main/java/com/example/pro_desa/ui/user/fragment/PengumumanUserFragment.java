package com.example.pro_desa.ui.user.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.R;
import com.example.pro_desa.adapter.AdapterListPengumuman;
import com.example.pro_desa.model.Pengumuman;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.repository.PengumumanRepository;
import com.example.pro_desa.utils.SharedPrefManager;
import com.example.pro_desa.viewmodels.PengumumanViewModel;

import java.util.ArrayList;

public class PengumumanUserFragment extends Fragment {
    private AdapterListPengumuman adapterListPengumuman;
    public SharedPrefManager sharedPrefManager;
    public ApiInterface apiInterface;
    Context context;

    PengumumanViewModel pengumumanViewModel;
    PengumumanRepository pengumumanRepository;
    LinearLayout linearLayout;
    SearchView searchView;

    public PengumumanUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pengumuman_user,
                container, false);

        pengumumanRepository = new PengumumanRepository();

        SwipeRefreshLayout swipeRefreshLayout = rootView.findViewById(R.id.refresh_ly);
        linearLayout = rootView.findViewById(R.id.ln_kosong_pengumuman);
        RecyclerView recyclerView  = rootView.findViewById(R.id.rv_pengumuman);

        searchView = rootView.findViewById(R.id.search_view);
        searchView.onActionViewExpanded(); //new Added line
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Cari pengumuman");
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
                adapterListPengumuman.getFilter().filter(newText);
                return false;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterListPengumuman);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstance){
        super.onCreate(savedInstance);
        adapterListPengumuman = new AdapterListPengumuman(getContext());

        sharedPrefManager = new SharedPrefManager(Myapp.getContext());

        pengumumanViewModel = ViewModelProviders.of(this).get(PengumumanViewModel.class);
        pengumumanViewModel.init();
        pengumumanViewModel.getPengumumanLiveData().observe(this, new Observer<ArrayList<Pengumuman>>() {
            @Override
            public void onChanged(ArrayList<Pengumuman> pengumumans) {
                adapterListPengumuman.setPengumumans(pengumumans);

                Log.d("asdcadscd", String.valueOf(adapterListPengumuman.getItemCount()));
                if (adapterListPengumuman.getItemCount() != 0){
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
        pengumumanViewModel.getPengumuman();
    }

    public void reloadPengumumanList() {
        pengumumanViewModel.getPengumuman();
        pengumumanRepository.getPengumuman();
    }
}