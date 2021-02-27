package com.example.pro_desa.model;

import com.example.pro_desa.network.response.PengaduanResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PengaduanList extends PengaduanResponse {
    @Expose
    @SerializedName("pengaduan")
    ArrayList<Pengaduan> pengaduans;

    @Expose
    @SerializedName("pengaduanUrl") String pengaduanUrl;

    public ArrayList<Pengaduan> getPengaduans() {
        return pengaduans;
    }

    public void setPengaduans(ArrayList<Pengaduan> pengaduans) {
        this.pengaduans = pengaduans;
    }

    public String getPengaduanUrl() {
        return pengaduanUrl;
    }

    public void setPengaduanUrl(String pengaduanUrl) {
        this.pengaduanUrl = pengaduanUrl;
    }
}
