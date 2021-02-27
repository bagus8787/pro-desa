package com.example.pro_desa.model.region;

import com.example.pro_desa.network.response.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Desa {
    @Expose
    @SerializedName("id_kel") String id_kel;
    @Expose
    @SerializedName("id_kec") String id_kec;
    @Expose
    @SerializedName("nama") String nama;
    @Expose
    @SerializedName("id_jenis") int id_jenis;

    public String getId_kel() {
        return id_kel;
    }

    public void setId_kel(String id_kel) {
        this.id_kel = id_kel;
    }

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
        if(obj instanceof Desa){
            Desa c = (Desa ) obj;
            if(c.getNama().equals(nama) && c.getId_kec()==id_kec ) return true;
        }

        return false;
    }
}
