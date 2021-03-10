package com.example.pro_desa.network.response;

import com.example.pro_desa.model.ListPermohonanSuratList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListPermohonanSuratResponse {
    @Expose
    @SerializedName("code") int code;
    @Expose
    @SerializedName("message") String message;
    @Expose
    @SerializedName("data")
    ArrayList<ListPermohonanSuratList> permohonanSuratLists;

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

    public ArrayList<ListPermohonanSuratList> getPermohonanSuratLists() {
        return permohonanSuratLists;
    }

    public void setPermohonanSuratLists(ArrayList<ListPermohonanSuratList> permohonanSuratLists) {
        this.permohonanSuratLists = permohonanSuratLists;
    }
}
