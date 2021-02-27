package com.example.pro_desa.network.response.region_response;

import com.example.pro_desa.model.region.Kabupaten;
import com.example.pro_desa.network.response.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class KabupatenResponse extends BaseResponse {
    @Expose
    @SerializedName("data")
    ArrayList<Kabupaten> kabupatens;

    public ArrayList<Kabupaten> getKabupatens() {
        return kabupatens;
    }

    public void setKabupatens(ArrayList<Kabupaten> kabupatens) {
        this.kabupatens = kabupatens;
    }
}
