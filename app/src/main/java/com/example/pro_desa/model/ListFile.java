package com.example.pro_desa.model;

import com.example.pro_desa.model.region.Desa;
import com.example.pro_desa.network.response.file_response.FileResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListFile extends FileResponse {
    @Expose
    @SerializedName("id") int id;
    @Expose
    @SerializedName("satuan") String satuan;
    @Expose
    @SerializedName("nama") String nama;
    @Expose
    @SerializedName("id_pend") int id_pend;
    @Expose
    @SerializedName("kategori") int kategori;
    @Expose
    @SerializedName("id_syarat") int id_syarat;

    public ListFile (int id_syarat){
        this.id_syarat = id_syarat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getId_pend() {
        return id_pend;
    }

    public void setId_pend(int id_pend) {
        this.id_pend = id_pend;
    }

    public int getKategori() {
        return kategori;
    }

    public void setKategori(int kategori) {
        this.kategori = kategori;
    }

    public int getId_syarat() {
        return id_syarat;
    }

    public void setId_syarat(int id_syarat) {
        this.id_syarat = id_syarat;
    }
    @Override
    public String toString() {
        return nama + ": " + String.valueOf(id) + " - " + String.valueOf(id_syarat);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ListFile){
            ListFile c = (ListFile ) obj;
//            if(c.getId_syarat()==id_syarat )
            if (String.valueOf(c.getId_syarat()).equals(String.valueOf(id_syarat)))
                return true;
        }

        return false;
    }
}
