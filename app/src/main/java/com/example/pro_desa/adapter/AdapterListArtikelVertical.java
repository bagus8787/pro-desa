package com.example.pro_desa.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.R;
import com.example.pro_desa.model.Artikel;
import com.example.pro_desa.ui.user.activity.DetailArtikelActivity;
import com.example.pro_desa.utils.SharedPrefManager;

import java.util.ArrayList;

public class AdapterListArtikelVertical extends RecyclerView.Adapter<AdapterListArtikelVertical.ArtikelViewHolder>{
    private ArrayList<Artikel> artikels;
    private ArrayList<Artikel> artikelsArrayartikels;
    private Context context;
    private SharedPrefManager sharedPrefManager;

    public AdapterListArtikelVertical(Context context){
        this.context = context;
        sharedPrefManager = new SharedPrefManager(Myapp.getContext());
    }

    @NonNull
    @Override
    public ArtikelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_artikel_vertical, parent, false);

        return new ArtikelViewHolder(view);
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ArtikelViewHolder holder, int position) {
        holder.setId(artikels.get(position).getId());
        holder.setJudul(artikels.get(position).getJudul());
        holder.setTgl_upload(artikels.get(position).getTgl_upload());
        holder.setIsi(artikels.get(position).getIsi());
    }

    @Override
    public int getItemCount() {
        return (artikels != null) ? artikels.size() : 0;
    }

    public void setArtikels(ArrayList<Artikel> artikels){
        this.artikels = artikels;

//        setModel Artikel ke aritkel baru
        artikelsArrayartikels = new ArrayList<>(artikels);
        notifyDataSetChanged();
    }

    public class ArtikelViewHolder extends RecyclerView.ViewHolder{
        View mView;
        int id;
        String judul, isi, tgl_upload;

        TextView rvJudul, rvTglUpload, rvIsi;

        public ArtikelViewHolder(View itemView){
            super(itemView);
            mView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, DetailArtikelActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra("IT_ID", id)
                            .putExtra("IT_JUDUL", judul)
                            .putExtra("IT_ISI", isi)
                            .putExtra("IT_TGL_UPLOAD", tgl_upload)
                    );

                }
            });
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setJudul (String judul){
            this.judul = judul;
            rvJudul = mView.findViewById(R.id.title_art);
            rvJudul.setText(judul.toUpperCase());
            Log.d("setJudul", String.valueOf(rvJudul));
        }

        public void setTgl_upload (String tgl_upload){
            this.tgl_upload = tgl_upload;
            rvTglUpload = mView.findViewById(R.id.tgl_art);
            rvTglUpload.setText(tgl_upload.toUpperCase());
            Log.d("setTgl", String.valueOf(rvTglUpload));
        }

        public void setIsi(String isi) {
            this.isi = String.valueOf(isi);
            rvIsi = mView.findViewById(R.id.isi_art);
            rvIsi.setText(isi.toUpperCase());

            Log.d("ISI", String.valueOf(isi));
        }
    }

    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Artikel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(artikelsArrayartikels);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Artikel arrayList : artikelsArrayartikels) {
                    if (arrayList.getJudul().toLowerCase().contains(filterPattern)) {
                        filteredList.add(arrayList);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            artikels.clear();
            artikels.addAll((ArrayList<Artikel>) results.values);
//            artikels = (ArrayList<Artikel>) results.values;
            notifyDataSetChanged();
        }
    };
}
