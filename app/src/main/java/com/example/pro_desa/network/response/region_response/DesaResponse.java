package com.example.pro_desa.network.response.region_response;

import com.example.pro_desa.model.region.Desa;
import com.example.pro_desa.network.response.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DesaResponse extends BaseResponse {
    @Expose
    @SerializedName("data")
    ArrayList<Desa> desas;

    public ArrayList<Desa> getDesas() {
        return desas;
    }

    public void setDesas(ArrayList<Desa> desas) {
        this.desas = desas;
    }
}
