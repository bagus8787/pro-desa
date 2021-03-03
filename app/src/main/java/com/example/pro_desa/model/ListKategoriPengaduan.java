package com.example.pro_desa.model;

import com.example.pro_desa.model.region.Kabupaten;
import com.example.pro_desa.network.response.kategori_response.KategoriPengaduanResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListKategoriPengaduan extends KategoriPengaduanResponse {
    @Expose
    @SerializedName("id") int id;
    @Expose
    @SerializedName("nama") String nama;
    @Expose
    @SerializedName("is_active") int is_active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    @Override
    public String toString() {
        return nama;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ListKategoriPengaduan){
            ListKategoriPengaduan c = (ListKategoriPengaduan ) obj;
            if(c.getNama().equals(nama) && c.getId()==id ) return true;
        }

        return false;
    }
}
