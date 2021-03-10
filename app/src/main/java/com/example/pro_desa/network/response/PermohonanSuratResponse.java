package com.example.pro_desa.network.response;

import com.example.pro_desa.model.PermohonanSurat;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PermohonanSuratResponse {
    @Expose
    @SerializedName("code") int code;
    @Expose
    @SerializedName("message") String message;
    @Expose
    @SerializedName("data")
    ArrayList<PermohonanSurat> permohonanSurats;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<PermohonanSurat> getPermohonanSurats() {
        return permohonanSurats;
    }

    public void setPermohonanSurats(ArrayList<PermohonanSurat> permohonanSurats) {
        this.permohonanSurats = permohonanSurats;
    }
}
