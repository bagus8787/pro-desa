package com.example.pro_desa.network.response;

import com.example.pro_desa.model.ArtikelList;
import com.example.pro_desa.model.PengaduanList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PengaduanResponse extends BaseResponse{
    @Expose
    @SerializedName("data")
    PengaduanList pengaduanList;

    public PengaduanList getPengaduanList() {
        return pengaduanList;
    }

    public void setPengaduanList(PengaduanList pengaduanList) {
        this.pengaduanList = pengaduanList;
    }
}
