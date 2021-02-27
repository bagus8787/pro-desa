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
import com.example.pro_desa.model.Pengumuman;
import com.example.pro_desa.ui.user.activity.DetailPengumumanActivity;
import com.example.pro_desa.utils.SharedPrefManager;

import java.util.ArrayList;

public class AdapterListPengumuman extends RecyclerView.Adapter<AdapterListPengumuman.PengumumanViewHolder>{
    private ArrayList<Pengumuman> pengumumans;
    private ArrayList<Pengumuman> artikelsArrayartikels;
    private Context context;
    private SharedPrefManager sharedPrefManager;

    public AdapterListPengumuman(Context context){
        this.context = context;
        sharedPrefManager = new SharedPrefManager(Myapp.getContext());
    }

    @NonNull
    @Override
    public PengumumanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_pengumuman, parent, false);
        return new PengumumanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PengumumanViewHolder holder, int position) {
        holder.setId(pengumumans.get(position).getId());
        holder.setJudul(pengumumans.get(position).getJudul_tautan());
        holder.setTgl_upload(pengumumans.get(position).getCreated_at());
        holder.setIsi(pengumumans.get(position).getTeks());
        holder.setTgl_mulai(pengumumans.get(position).getTanggal_berlaku());
        holder.setTgl_berakhir(pengumumans.get(position).getTanggal_berakhir());
        holder.setDibuatOleh(pengumumans.get(position).getDari());

    }

    @Override
    public int getItemCount() {
        return (pengumumans != null) ? pengumumans.size() : 0;
    }

    public void setPengumumans(ArrayList<Pengumuman> pengumumans){
        this.pengumumans = pengumumans;
        artikelsArrayartikels = new ArrayList<>(pengumumans);
        notifyDataSetChanged();
    }

    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Pengumuman> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(artikelsArrayartikels);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Pengumuman arrayList : artikelsArrayartikels) {
                    if (arrayList.getJudul_tautan().toLowerCase().contains(filterPattern)) {
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
            pengumumans.clear();
            pengumumans.addAll((ArrayList<Pengumuman>) results.values);
//            artikels = (ArrayList<Artikel>) results.values;
            notifyDataSetChanged();
        }
    };

    public class PengumumanViewHolder extends RecyclerView.ViewHolder{
        View mView;
        int id;
        String judul, isi, tgl_upload, tgl_berlaku, tgl_berakhir, dibuat_oleh;

        TextView rvJudul, rvTglUpload, rvIsi;

        public PengumumanViewHolder(View itemView){
            super(itemView);
            mView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, DetailPengumumanActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra("IT_ID", id)
                            .putExtra("IT_JUDUL", judul)
                            .putExtra("IT_ISI", isi)
                            .putExtra("IT_TGL_UPLOAD", tgl_upload)
                            .putExtra("IT_TGL_MULAI", tgl_berlaku)
                            .putExtra("IT_TGL_BERAKHIR", tgl_berakhir)
                            .putExtra("IT_DARI", dibuat_oleh)
                    );

                }
            });
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setJudul (String judul){
            this.judul = judul;
            rvJudul = mView.findViewById(R.id.txt_judul_pe);
            rvJudul.setText(judul.toUpperCase());
            Log.d("setJudul", String.valueOf(rvJudul));
        }

        public void setTgl_upload (String tgl_upload){
            this.tgl_upload = tgl_upload;
            rvTglUpload = mView.findViewById(R.id.txt_tgl_pe);
            rvTglUpload.setText(tgl_upload.toUpperCase());
            Log.d("setTgl", String.valueOf(rvTglUpload));
        }

        public void setIsi(String isi) {
            this.isi = String.valueOf(isi);
            rvIsi = mView.findViewById(R.id.txt_ket_pe);
            rvIsi.setText(isi.toUpperCase());

            Log.d("ISI", String.valueOf(isi));
        }

        public void setTgl_mulai(String tanggal_berlaku) {
            this.tgl_berlaku = tanggal_berlaku;
        }

        public void setTgl_berakhir(String tanggal_berakhir) {
            this.tgl_berakhir = tanggal_berakhir;
        }

        public void setDibuatOleh(String dari) {
            this.dibuat_oleh = dari;
        }
    }
}
