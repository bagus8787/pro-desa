package com.example.pro_desa.model.region;

import com.example.pro_desa.network.response.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Provinsi {
    @Expose
    @SerializedName("id_prov") String id_prov;

    @Expose
    @SerializedName("nama") String nama;

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

    @Override
    public String toString() {
        return nama;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Provinsi){
            Provinsi c = (Provinsi ) obj;
            if(c.getNama().equals(nama) && c.getId_prov()==id_prov ) return true;
        }

        return false;
    }
}
