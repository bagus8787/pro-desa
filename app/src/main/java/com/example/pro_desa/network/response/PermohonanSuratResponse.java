package com.example.pro_desa.network.response;

import com.example.pro_desa.model.PermohonanSuratList;
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
    ArrayList<PermohonanSuratList> permohonanSuratLists;

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

    public ArrayList<PermohonanSuratList> getPermohonanSuratLists() {
        return permohonanSuratLists;
    }

    public void setPermohonanSuratLists(ArrayList<PermohonanSuratList> permohonanSuratLists) {
        this.permohonanSuratLists = permohonanSuratLists;
    }
}
