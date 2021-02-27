package com.example.pro_desa.model;

import com.example.pro_desa.network.response.BantuanResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BantuanList extends BantuanResponse {
    @Expose
    @SerializedName("bantuan")
    ArrayList<Bantuan> bantuans;

    @Expose
    @SerializedName("bantuanUrl") String bantuanUrl;

    public ArrayList<Bantuan> getBantuans() {
        return bantuans;
    }

    public void setBantuans(ArrayList<Bantuan> bantuans) {
        this.bantuans = bantuans;
    }

    public String getBantuanUrl() {
        return bantuanUrl;
    }

    public void setBantuanUrl(String bantuanUrl) {
        this.bantuanUrl = bantuanUrl;
    }
}
