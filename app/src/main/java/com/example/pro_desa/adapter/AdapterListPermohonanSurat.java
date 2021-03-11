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
import com.example.pro_desa.model.PermohonanSurat;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.ui.user.activity.DetailPermohonanSuratActivity;
import com.example.pro_desa.ui.user.activity.SyaratPermohonanSuratActivity;
import com.example.pro_desa.utils.SharedPrefManager;

import java.util.ArrayList;

public class AdapterListPermohonanSurat extends RecyclerView.Adapter<AdapterListPermohonanSurat.PermohonanSuratViewHolder>{
    private ArrayList<PermohonanSurat> permohonanSuratLists;
    private ArrayList<PermohonanSurat> permohonanSuratsArrayartikelLists;
    private Context context;
    private SharedPrefManager sharedPrefManager;
    private ApiInterface apiInterface;

    public AdapterListPermohonanSurat(Context context){
        this.context = context;
        sharedPrefManager = new SharedPrefManager(Myapp.getContext());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

    }

    @NonNull
    @Override
    public PermohonanSuratViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_permohonan_surat, parent, false);
        return new PermohonanSuratViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PermohonanSuratViewHolder holder, int position) {
        holder.setId(permohonanSuratLists.get(position).getId());
        holder.setJudul(permohonanSuratLists.get(position).getNama_kategori_surat());
        holder.setStatus(permohonanSuratLists.get(position).getStatus());
        holder.setTgl(permohonanSuratLists.get(position).getCreated_at());

    }

    @Override
    public int getItemCount() {
        return (permohonanSuratLists != null) ? permohonanSuratLists.size() : 0;
    }

    public void setPermohonanSuratLists(ArrayList<PermohonanSurat> permohonanSuratLists){
        this.permohonanSuratLists = permohonanSuratLists;
        permohonanSuratsArrayartikelLists = new ArrayList<>(permohonanSuratLists);
        notifyDataSetChanged();
    }

    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<PermohonanSurat> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(permohonanSuratLists);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (PermohonanSurat arrayList : permohonanSuratsArrayartikelLists) {
                    if (arrayList.getNama_kategori_surat().toLowerCase().contains(filterPattern)) {
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
            permohonanSuratLists.clear();
            permohonanSuratLists.addAll((ArrayList<PermohonanSurat>) results.values);
//            artikels = (ArrayList<Artikel>) results.values;
            notifyDataSetChanged();
        }
    };

    public class PermohonanSuratViewHolder extends RecyclerView.ViewHolder {
        View mView;
        int id, status;
        String nama, url_gambar,id_syarat, created;

        TextView rvJudul, rvTglUpload, rvIsi;

        public PermohonanSuratViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, DetailPermohonanSuratActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra("IT_ID", String.valueOf(id))
                            .putExtra("IT_NAMA", nama)
                            .putExtra("IT_ID_SYARAT", id_syarat)
                            .putExtra("IT_URL_GAMBAR_FILE", url_gambar)
                            .putExtra("IT_STATUS", String.valueOf(status))
                            .putExtra("IT_CREATED", created)
                    );

//                    Log.d("id_leter", String.valueOf(id));

                    sharedPrefManager.saveSPString(SharedPrefManager.SP_ID_LETER, String.valueOf(id));

                }
            });
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setJudul(String judul) {
            this.nama = judul;
            rvJudul = mView.findViewById(R.id.txt_keterangan_surat);
//            rvJudul.setText(StringUtils.capitalize(judul.toLowerCase().trim()));

            rvJudul.setText(judul.toUpperCase());
            Log.d("setJudul", String.valueOf(rvJudul));
        }


        public void setStatus(int status) {
            this.status = status;
        }

        public void setTgl(String created_at) {
            this.created = created_at;
        }
    }
}
