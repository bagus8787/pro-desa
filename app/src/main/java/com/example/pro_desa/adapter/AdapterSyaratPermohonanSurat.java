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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.R;
import com.example.pro_desa.model.ListFile;
import com.example.pro_desa.model.SyaratPermohonanSuratList;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.network.response.SyaratPermohonanSuratResponse;
import com.example.pro_desa.network.response.file_response.PermohonanFileResponse;
import com.example.pro_desa.ui.user.activity.PilihFileActivity;
import com.example.pro_desa.utils.SharedPrefManager;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class AdapterSyaratPermohonanSurat extends RecyclerView.Adapter<AdapterSyaratPermohonanSurat.SyaratPermohonanSuratViewHolder>{
    private ArrayList<SyaratPermohonanSuratList> syaratPermohonanSuratLists;
    private ArrayList<SyaratPermohonanSuratList> syaratPermohonanSuratListArrayList;
    private Context context;

    private ArrayList<ListFile> listFile = new ArrayList<ListFile>();

    private ApiInterface apiInterface;
    private SharedPrefManager sharedPrefManager;

    public static final int PICK_IMAGE = 1;
    public static final int PERMISSION_REQUEST_STORAGE = 2;

    File file;
    Uri uri;


    int no = 0;

    ImageView img_check;

    public AdapterSyaratPermohonanSurat(Context context){
        this.context = context;
//        this.listFile.add(new ListFile());
        sharedPrefManager = new SharedPrefManager(Myapp.getContext());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void SetFiles(ArrayList<ListFile> listFiles){
        this.listFile = listFiles;
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
        holder.surat_format_id(syaratPermohonanSuratLists.get(position).getSurat_format_id());
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
        int id, surat_format_id;
        String nama, id_syarat;
        ImageView img_check;
        ListFile listfile;
        String url_gambar, url_gambar_nama;
        //        int no = 0;
        int total = 0;

        TextView rvJudul, rvTglUpload, rvNo, rvId, id_file;
        Button btn_upload_syarat;

        public SyaratPermohonanSuratViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            btn_upload_syarat = itemView.findViewById(R.id.btn_upload_syarat);
            btn_upload_syarat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("nama_file", nama);
                    context.startActivity(new Intent(context, PilihFileActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra("IT_REF_SYARAT_ID", String.valueOf(id))
                            .putExtra("IT_NAMA", nama)
                            .putExtra("IT_URL_GAMBAR", url_gambar)
                            .putExtra("IT_URL_GAMBAR_NAMA", url_gambar_nama)
                    );

                }
            });

        }

        public void setId(int id) {
            this.id = id;

            rvId = mView.findViewById(R.id.ref_syarat_id);
            rvId.setText(String.valueOf(id));
            Log.d("id_syaratv", String.valueOf(id));

            for (ListFile file: listFile){

                if (String.valueOf(id).equals(String.valueOf(file.getId_syarat()))){
                    mView.findViewById(R.id.img_check).setVisibility(View.VISIBLE);
                    id_file = mView.findViewById(R.id.id_syarat);

                    id_file.setText(String.valueOf(file.getId()));

                    Log.d("akla", String.valueOf(id) + "=" + String.valueOf(file.getId_syarat()));

                    url_gambar = "http://222.124.168.221:8500/demo/prodesa-putat/desa/upload/dokumen/" + file.getSatuan();
                    url_gambar_nama = file.getNama();

                    Log.d("urlgambar", url_gambar);

//                        return;
                    break;
                } else {
                    mView.findViewById(R.id.img_check).setVisibility(View.INVISIBLE);
                    url_gambar = "null";
                    url_gambar_nama = "Nama File";
//                    break;
                }
//
            }
        }

        public void surat_format_id(int surat_format_id) {
            this.surat_format_id = surat_format_id;

            Log.d("surat_format_id", String.valueOf(surat_format_id));
        }

        public void setJudul(String judul) {
            this.nama = judul;
            rvJudul = mView.findViewById(R.id.txt_nama_syarat);
            rvJudul.setText(judul.toUpperCase());
            Log.d("setJudul", String.valueOf(rvJudul));
        }

        public void setNo(int nou) {

        }


//        public void setListFile(ListFile listfile) {
//            this.listfile = listfile;
//
//        }
    }

//


}