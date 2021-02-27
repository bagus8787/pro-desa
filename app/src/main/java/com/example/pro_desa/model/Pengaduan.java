package com.example.pro_desa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pengaduan {
    @Expose
    @SerializedName("id") int id;
    @Expose
    @SerializedName("title") String title;
    @Expose
    @SerializedName("description") String description;
    @Expose
    @SerializedName("pend_id") int pend_id;
    @Expose
    @SerializedName("foto_pendukung") String foto_pendukung;
    @Expose
    @SerializedName("created_at") String created_at;
    @Expose
    @SerializedName("updated_at") String updated_at;
    @Expose
    @SerializedName("kategori_pengaduan") int kategori_pengaduan;
    @Expose
    @SerializedName("detail_lokasi") String detail_lokasi;
    @Expose
    @SerializedName("latitude") String latitude;
    @Expose
    @SerializedName("longitude") String longitude;
    @Expose
    @SerializedName("is_masyarakat") int is_masyarakat;
    @Expose
    @SerializedName("nama_kategori") String nama_kategori;
    @Expose
    @SerializedName("nama_pengadu") String nama_pengadu;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPend_id() {
        return pend_id;
    }

    public void setPend_id(int pend_id) {
        this.pend_id = pend_id;
    }

    public String getFoto_pendukung() {
        return foto_pendukung;
    }

    public void setFoto_pendukung(String foto_pendukung) {
        this.foto_pendukung = foto_pendukung;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getKategori_pengaduan() {
        return kategori_pengaduan;
    }

    public void setKategori_pengaduan(int kategori_pengaduan) {
        this.kategori_pengaduan = kategori_pengaduan;
    }

    public String getDetail_lokasi() {
        return detail_lokasi;
    }

    public void setDetail_lokasi(String detail_lokasi) {
        this.detail_lokasi = detail_lokasi;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getIs_masyarakat() {
        return is_masyarakat;
    }

    public void setIs_masyarakat(int is_masyarakat) {
        this.is_masyarakat = is_masyarakat;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public String getNama_pengadu() {
        return nama_pengadu;
    }

    public void setNama_pengadu(String nama_pengadu) {
        this.nama_pengadu = nama_pengadu;
    }
}
