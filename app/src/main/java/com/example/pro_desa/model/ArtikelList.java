package com.example.pro_desa.model;

import com.example.pro_desa.network.response.ArtikelResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ArtikelList extends ArtikelResponse {
    @Expose
    @SerializedName("artikel")
    ArrayList<Artikel> artikels;

    @Expose
    @SerializedName("artikelUrl") String artikelUrl;


    public ArrayList<Artikel> getArtikels() {
        return artikels;
    }

    public void setArtikels(ArrayList<Artikel> artikels) {
        this.artikels = artikels;
    }

    public String getArtikelUrl() {
        return artikelUrl;
    }

    public void setArtikelUrl(String artikelUrl) {
        this.artikelUrl = artikelUrl;
    }
}
