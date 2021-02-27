package com.example.pro_desa.network.response.region_response;

import com.example.pro_desa.model.region.Kecamatan;
import com.example.pro_desa.network.response.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class KecamatanResponse extends BaseResponse {
    @Expose
    @SerializedName("data")
    ArrayList<Kecamatan> kecamatans;

    public ArrayList<Kecamatan> getKecamatans() {
        return kecamatans;
    }

    public void setKecamatans(ArrayList<Kecamatan> kecamatans) {
        this.kecamatans = kecamatans;
    }
}
