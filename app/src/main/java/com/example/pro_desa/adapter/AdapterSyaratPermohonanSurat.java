package com.example.pro_desa.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.R;
import com.example.pro_desa.model.SyaratPermohonanSuratList;
import com.example.pro_desa.ui.user.activity.PilihFileActivity;
import com.example.pro_desa.utils.SharedPrefManager;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AdapterSyaratPermohonanSurat extends RecyclerView.Adapter<AdapterSyaratPermohonanSurat.SyaratPermohonanSuratViewHolder>{
    private ArrayList<SyaratPermohonanSuratList> syaratPermohonanSuratLists;
    private ArrayList<SyaratPermohonanSuratList> syaratPermohonanSuratListArrayList;
    private Context context;
    private SharedPrefManager sharedPrefManager;

    public static final int PICK_IMAGE = 1;
    public static final int PERMISSION_REQUEST_STORAGE = 2;

    File file;
    Uri uri;

    int no = 0;

    public AdapterSyaratPermohonanSurat(Context context){
        this.context = context;
        sharedPrefManager = new SharedPrefManager(Myapp.getContext());
    }

    @NonNull
    @Override
    public SyaratPermohonanSuratViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_syarat_permohonan_surat, parent, false);
        return new SyaratPermohonanSuratViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SyaratPermohonanSuratViewHolder holder, int position) {
        holder.setId(syaratPermohonanSuratLists.get(position).getRef_syarat_id());
        holder.setJudul(syaratPermohonanSuratLists.get(position).getRef_syarat_nama());
        holder.setNo(no);

    }

    @Override
    public int getItemCount() {
        return (syaratPermohonanSuratLists != null) ? syaratPermohonanSuratLists.size() : 0;
    }

    public void setPermohonanSuratLists(ArrayList<SyaratPermohonanSuratList> syaratPermohonanSuratLists){
        this.syaratPermohonanSuratLists = syaratPermohonanSuratLists;
        syaratPermohonanSuratListArrayList = new ArrayList<>(syaratPermohonanSuratLists);
        notifyDataSetChanged();
    }

    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<SyaratPermohonanSuratList> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(syaratPermohonanSuratLists);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (SyaratPermohonanSuratList arrayList : syaratPermohonanSuratListArrayList) {
                    if (arrayList.getRef_syarat_nama().toLowerCase().contains(filterPattern)) {
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
            syaratPermohonanSuratLists.clear();
            syaratPermohonanSuratLists.addAll((ArrayList<SyaratPermohonanSuratList>) results.values);
//            artikels = (ArrayList<Artikel>) results.values;
            notifyDataSetChanged();
        }
    };

    public class SyaratPermohonanSuratViewHolder extends RecyclerView.ViewHolder {
        View mView;
        int id;
        String nama;
//        int no = 0;
        int total = 0;

        TextView rvJudul, rvTglUpload, rvNo;
        Button btn_upload_syarat;

        public SyaratPermohonanSuratViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            btn_upload_syarat = itemView.findViewById(R.id.btn_upload_syarat);
            btn_upload_syarat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("nama_file", nama);
                    Log.d("id_file", String.valueOf(id));

                    context.startActivity(new Intent(context, PilihFileActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra("IT_REF_SYARAT_ID", String.valueOf(id))
                            .putExtra("IT_NAMA", nama)
                    );

                }
            });

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    context.startActivity(new Intent(context, SyaratPermohonanSuratActivity.class)
////                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
////                            .putExtra("IT_ID", id)
////                            .putExtra("IT_NAMA", nama)
////                    );
//
//                    Log.d("nama_file", nama);
//                    Log.d("id_file", String.valueOf(id));
//
//                }
//            });

//            total = getItemCount();
//
//            for(int no = 0; no < total; ++no){
//                rvNo = mView.findViewById(R.id.txt_no);
//                rvNo.setText(String.valueOf(no));
//            }
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setJudul(String judul) {
            this.nama = judul;
            rvJudul = mView.findViewById(R.id.txt_nama_syarat);
            rvJudul.setText(judul.toUpperCase());
            Log.d("setJudul", String.valueOf(rvJudul));
        }

        public void setNo(int nou) {
            int total = getItemCount();
            no = nou;

            if (nou < total){
              ++no;
            } else {
//                no = 0;
            }

            rvNo = mView.findViewById(R.id.txt_no);
            rvNo.setText(String.valueOf(nou + "."));
            Log.d("nour", String.valueOf(nou));

        }

    }

//


}