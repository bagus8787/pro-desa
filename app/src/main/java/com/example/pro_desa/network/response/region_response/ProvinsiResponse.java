package com.example.pro_desa.network.response.region_response;

import com.example.pro_desa.model.region.Provinsi;
import com.example.pro_desa.network.response.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProvinsiResponse extends BaseResponse {
    @Expose
    @SerializedName("data")
    ArrayList<Provinsi> provinsis;

    public ArrayList<Provinsi> getProvinsis() {
        return provinsis;
    }

    public void setProvinsis(ArrayList<Provinsi> provinsis) {
        this.provinsis = provinsis;
    }
}
