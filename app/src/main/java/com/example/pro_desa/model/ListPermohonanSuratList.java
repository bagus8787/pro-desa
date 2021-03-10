package com.example.pro_desa.model;

import com.example.pro_desa.network.response.ListPermohonanSuratResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListPermohonanSuratList extends ListPermohonanSuratResponse {
    @Expose
    @SerializedName("id") int id;
    @Expose
    @SerializedName("nama") String nama;
    @Expose
    @SerializedName("url_surat") String url_surat;
    @Expose
    @SerializedName("kode_surat") String kode_surat;
    @Expose
    @SerializedName("lampiran") String lampiran;
    @Expose
    @SerializedName("kunci") int kunci;
    @Expose
    @SerializedName("favorit") int favorit;
    @Expose
    @SerializedName("jenis") int jenis;
    @Expose
    @SerializedName("icon") String icon;
    @Expose
    @SerializedName("mandiri") int mandiri;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUrl_surat() {
        return url_surat;
    }

    public void setUrl_surat(String url_surat) {
        this.url_surat = url_surat;
    }

    public String getKode_surat() {
        return kode_surat;
    }

    public void setKode_surat(String kode_surat) {
        this.kode_surat = kode_surat;
    }

    public String getLampiran() {
        return lampiran;
    }

    public void setLampiran(String lampiran) {
        this.lampiran = lampiran;
    }

    public int getKunci() {
        return kunci;
    }

    public void setKunci(int kunci) {
        this.kunci = kunci;
    }

    public int getFavorit() {
        return favorit;
    }

    public void setFavorit(int favorit) {
        this.favorit = favorit;
    }

    public int getJenis() {
        return jenis;
    }

    public void setJenis(int jenis) {
        this.jenis = jenis;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getMandiri() {
        return mandiri;
    }

    public void setMandiri(int mandiri) {
        this.mandiri = mandiri;
    }
}
