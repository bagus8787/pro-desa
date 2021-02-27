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
import com.example.pro_desa.model.Pengaduan;
import com.example.pro_desa.ui.user.activity.DetailPengaduanActivity;
import com.example.pro_desa.utils.SharedPrefManager;

import java.util.ArrayList;

public class AdapterListPengaduan extends RecyclerView.Adapter<AdapterListPengaduan.PengaduanViewHolder> {
    private ArrayList<Pengaduan> pengaduans;
    private ArrayList<Pengaduan> ArrayPengaduans;
    private Context context;
    private SharedPrefManager sharedPrefManager;

    public AdapterListPengaduan(Context context){
        this.context = context;
        sharedPrefManager = new SharedPrefManager(Myapp.getContext());
    }

    @NonNull
    @Override
    public PengaduanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_pengaduan, parent, false);

        return new PengaduanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PengaduanViewHolder holder, int position) {
        holder.setId(pengaduans.get(position).getId());
        holder.setDescription(pengaduans.get(position).getDescription());
        holder.setJudul(pengaduans.get(position).getTitle());
        holder.setTgl_upload(pengaduans.get(position).getCreated_at());
        
        holder.setKategori(pengaduans.get(position).getNama_kategori());
        holder.setDetailLokasi(pengaduans.get(position).getDetail_lokasi());

    }

    @Override
    public int getItemCount() {
        return (pengaduans != null) ? pengaduans.size() : 0;
    }

    public void setPengaduan(ArrayList<Pengaduan> pengaduanss){
        this.pengaduans = pengaduanss;
        ArrayPengaduans = new ArrayList<>(pengaduans);
        notifyDataSetChanged();
    }

    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Pengaduan> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(ArrayPengaduans);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Pengaduan arrayList : ArrayPengaduans) {
                    if (arrayList.getTitle().toLowerCase().contains(filterPattern)) {
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
            pengaduans.clear();
            pengaduans.addAll((ArrayList<Pengaduan>) results.values);
//            artikels = (ArrayList<Pengaduan>) results.values;
            notifyDataSetChanged();
        }
    };

    public class PengaduanViewHolder extends RecyclerView.ViewHolder{
        View mView;
        int id;
        String judul, isi, tgl_upload, desc, nama_kategori, detail_lokasi;

        TextView rvJudul, rvTglUpload;

        public PengaduanViewHolder(View itemView){
            super(itemView);
            mView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, DetailPengaduanActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra("IT_ID", id)
                            .putExtra("IT_JUDUL", judul)
                            .putExtra("IT_DESC", desc)
                            .putExtra("IT_ISI", isi)
                            .putExtra("IT_TGL_UPLOAD", tgl_upload)
                            .putExtra("IT_DETAIL_LOKASI", detail_lokasi)
                            .putExtra("IT_KATEGORI", nama_kategori)
                    );

                }
            });
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setJudul (String judul){
            this.judul = judul;
            rvJudul = mView.findViewById(R.id.txt_judul_pengaduan);
            rvJudul.setText(judul.toUpperCase());
            Log.d("setJudul", String.valueOf(rvJudul));
        }

        public void setTgl_upload (String tgl_upload){
            this.tgl_upload = tgl_upload;
            rvTglUpload = mView.findViewById(R.id.txt_created_pengaduan);
            rvTglUpload.setText(tgl_upload.toUpperCase());
            Log.d("setTgl", String.valueOf(rvTglUpload));
        }

        public void setIsi(String isi) {
            this.isi = String.valueOf(isi);
            Log.d("ISI", String.valueOf(isi));
        }

        public void setKategori(String nama_kategori) {
            this.nama_kategori = nama_kategori;
        }

        public void setDetailLokasi(String detail_lokasi) {
            this.detail_lokasi = detail_lokasi;
        }

        public void setDescription(String description) {
            this.desc = description;
        }
    }
}
