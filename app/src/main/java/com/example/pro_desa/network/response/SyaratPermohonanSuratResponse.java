package com.example.pro_desa.network.response;

import com.example.pro_desa.model.PermohonanSuratList;
import com.example.pro_desa.model.SyaratPermohonanSuratList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SyaratPermohonanSuratResponse {
    @Expose
    @SerializedName("code") int code;
    @Expose
    @SerializedName("message") String message;
    @Expose
    @SerializedName("data")
    ArrayList<SyaratPermohonanSuratList> syaratPermohonanSuratLists;

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

    public ArrayList<SyaratPermohonanSuratList> getSyaratPermohonanSuratLists() {
        return syaratPermohonanSuratLists;
    }

    public void setSyaratPermohonanSuratLists(ArrayList<SyaratPermohonanSuratList> syaratPermohonanSuratLists) {
        this.syaratPermohonanSuratLists = syaratPermohonanSuratLists;
    }
}
