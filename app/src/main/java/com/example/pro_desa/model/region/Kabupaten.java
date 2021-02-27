package com.example.pro_desa.model.region;

import com.example.pro_desa.network.response.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kabupaten {
    @Expose
    @SerializedName("id_kab") String id_kab;
    @Expose
    @SerializedName("id_prov") String id_prov;
    @Expose
    @SerializedName("nama") String nama;
    @Expose
    @SerializedName("id_jenis") int id_jenis;

    public String getId_kab() {
        return id_kab;
    }

    public void setId_kab(String id_kab) {
        this.id_kab = id_kab;
    }

    public String getId_prov() {
        return id_prov;
    }

    public void setId_prov(String id_prov) {
        this.id_prov = id_prov;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getId_jenis() {
        return id_jenis;
    }

    public void setId_jenis(int id_jenis) {
        this.id_jenis = id_jenis;
    }

    @Override
    public String toString() {
        return nama;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Kabupaten){
            Kabupaten c = (Kabupaten ) obj;
            if(c.getNama().equals(nama) && c.getId_kab()==id_kab ) return true;
        }

        return false;
    }
}
