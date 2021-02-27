package com.example.pro_desa.network.response;

import com.example.pro_desa.model.ArtikelList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArtikelResponse extends BaseResponse{
    @Expose
    @SerializedName("data")
    ArtikelList artikelList;

    public ArtikelList getArtikelList() {
        return artikelList;
    }

    public void setArtikelList(ArtikelList artikelList) {
        this.artikelList = artikelList;
    }
}
