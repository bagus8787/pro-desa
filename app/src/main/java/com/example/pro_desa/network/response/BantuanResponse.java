package com.example.pro_desa.network.response;

import com.example.pro_desa.model.ArtikelList;
import com.example.pro_desa.model.BantuanList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BantuanResponse extends BaseResponse {
    @Expose
    @SerializedName("data")
    BantuanList bantuanList;

    public BantuanList getBantuanList() {
        return bantuanList;
    }

    public void setBantuanList(BantuanList bantuanList) {
        this.bantuanList = bantuanList;
    }
}
