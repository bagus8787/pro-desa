package com.example.pro_desa.network.response;

import com.example.pro_desa.model.PengaduanList;
import com.example.pro_desa.model.Pengumuman;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PengumumanResponse extends BaseResponse{
    @Expose
    @SerializedName("data")
    ArrayList<Pengumuman> pengumumanList;

    public ArrayList<Pengumuman> getPengumumanList() {
        return pengumumanList;
    }

    public void setPengumumanList(ArrayList<Pengumuman> pengumumanList) {
        this.pengumumanList = pengumumanList;
    }
}
