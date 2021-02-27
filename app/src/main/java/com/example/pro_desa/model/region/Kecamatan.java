package com.example.pro_desa.model.region;

import com.example.pro_desa.network.response.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kecamatan {
    @Expose
    @SerializedName("id_kec") String id_kec;
    @Expose
    @SerializedName("nama") String nama;
    @Expose
    @SerializedName("id_kab") String id_kab;

    public String getId_kec() {
        return id_kec;
    }

    public void setId_kec(String id_kec) {
        this.id_kec = id_kec;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getId_kab() {
        return id_kab;
    }

    public void setId_kab(String id_kab) {
        this.id_kab = id_kab;
    }

    @Override
    public String toString() {
        return nama;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Kecamatan){
            Kecamatan c = (Kecamatan ) obj;
            if(c.getNama().equals(nama) && c.getId_kec()==id_kec ) return true;
        }

        return false;
    }
}
