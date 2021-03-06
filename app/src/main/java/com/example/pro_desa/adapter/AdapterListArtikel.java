package com.example.pro_desa.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro_desa.Myapp;
import com.example.pro_desa.R;
import com.example.pro_desa.model.Artikel;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.ui.user.activity.DetailArtikelActivity;
import com.example.pro_desa.utils.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.nio.file.attribute.PosixFileAttributes;
import java.util.ArrayList;

import retrofit2.Callback;

public class AdapterListArtikel extends RecyclerView.Adapter<AdapterListArtikel.ArtikelViewHolder> {
    private ArrayList<Artikel> artikels;
    private Context context;
    private SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;

    public AdapterListArtikel(Context context){
        this.context = context;
        sharedPrefManager = new SharedPrefManager(Myapp.getContext());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    @NonNull
    @Override
    public ArtikelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_artikel, parent, false);

        return new ArtikelViewHolder(view);
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ArtikelViewHolder holder, int position) {
        holder.setId(artikels.get(position).getId());
        holder.setJudul(artikels.get(position).getJudul());
        holder.setTgl_upload(artikels.get(position).getTgl_upload());
        holder.setIsi(artikels.get(position).getIsi());
        holder.setPhoto(artikels.get(position).getGambar());
//        Html.fromHtml(list.get(position).getTexto())
    }

    @Override
    public int getItemCount() {
        return (artikels != null) ? artikels.size() : 0;
//        return Integer.MAX_VALUE;
    }

    public void setArtikels(ArrayList<Artikel> artikelss){
        this.artikels = artikelss;
        notifyDataSetChanged();
    }

    public class ArtikelViewHolder extends RecyclerView.ViewHolder{
        View mView;
        int id;
        String judul, isi, tgl_upload, url_gambar;

        TextView rvJudul, rvTglUpload;
        ImageView rv_img_berita;

        public ArtikelViewHolder(View itemView){
            super(itemView);
            mView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, DetailArtikelActivity.class)
                            .putExtra("IT_ID", id)
                            .putExtra("IT_JUDUL", judul)
                            .putExtra("IT_ISI", isi)
                            .putExtra("IT_TGL_UPLOAD", tgl_upload)
                            .putExtra("IT_URL_GAMBAR", url_gambar)
                    );

                }
            });
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setJudul (String judul){
            this.judul = judul;
            rvJudul = mView.findViewById(R.id.txt_judul_berita);
            rvJudul.setText(judul.toUpperCase());
            Log.d("setJudul", String.valueOf(rvJudul));
        }

        public void setTgl_upload (String tgl_upload){
            this.tgl_upload = tgl_upload;
            rvTglUpload = mView.findViewById(R.id.txt_tgl_upload);
            rvTglUpload.setText(tgl_upload.toUpperCase());
            Log.d("setTgl", String.valueOf(rvTglUpload));
        }

        public void setIsi(String isi) {
            this.isi = String.valueOf(isi);
            Log.d("ISI", String.valueOf(isi));
        }

        public void setPhoto(String gambar) {
            this.url_gambar = gambar;
//            http://222.124.168.221:8500/demo/prodesa/
            url_gambar = "http://222.124.168.221:8500/demo/prodesa-putat/desa/upload/artikel/" + gambar;

            Log.d("url_gambar", url_gambar);

            rv_img_berita = mView.findViewById(R.id.img_berita);

            rv_img_berita.setScaleType(ImageView.ScaleType.CENTER_CROP);

            Picasso.with(context)
                    .load(url_gambar)
                    .error(R.drawable.bg_null)
                    .into(rv_img_berita);

        }
    }
}
