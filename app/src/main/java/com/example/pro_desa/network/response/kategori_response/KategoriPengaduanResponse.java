package com.example.pro_desa.network.response.kategori_response;

import com.example.pro_desa.model.ListKategoriPengaduan;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class KategoriPengaduanResponse {
    @Expose
    @SerializedName("code") int code;
    @Expose
    @SerializedName("message") String message;
    @Expose
    @SerializedName("data")
    ArrayList<ListKategoriPengaduan> listKategoriPengaduans;

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

    public ArrayList<ListKategoriPengaduan> getListKategoriPengaduans() {
        return listKategoriPengaduans;
    }

    public void setListKategoriPengaduans(ArrayList<ListKategoriPengaduan> listKategoriPengaduans) {
        this.listKategoriPengaduans = listKategoriPengaduans;
    }
}
