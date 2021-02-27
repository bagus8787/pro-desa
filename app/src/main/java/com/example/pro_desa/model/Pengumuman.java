package com.example.pro_desa.model;

import com.example.pro_desa.network.response.PengaduanResponse;
import com.example.pro_desa.network.response.PengumumanResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pengumuman extends PengumumanResponse {
    @Expose
    @SerializedName("id")
    int id;
    @Expose
    @SerializedName("judul_tautan")
    String judul_tautan;
    @Expose
    @SerializedName("teks")
    String teks;
    @Expose
    @SerializedName("dari")
    String dari;
    @Expose
    @SerializedName("created_by")
    int created_by;
    @Expose
    @SerializedName("created_at")
    String created_at;
    @Expose
    @SerializedName("updated_at")
    String updated_at;
    @Expose
    @SerializedName("file")
    String file;
    @Expose
    @SerializedName("tanggal_berakhir")
    String tanggal_berakhir;
    @Expose
    @SerializedName("tanggal_berlaku")
    String tanggal_berlaku;
    @Expose
    @SerializedName("is_read")
    int is_read;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul_tautan() {
        return judul_tautan;
    }

    public void setJudul_tautan(String judul_tautan) {
        this.judul_tautan = judul_tautan;
    }

    public String getTeks() {
        return teks;
    }

    public void setTeks(String teks) {
        this.teks = teks;
    }

    public String getDari() {
        return dari;
    }

    public void setDari(String dari) {
        this.dari = dari;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getTanggal_berakhir() {
        return tanggal_berakhir;
    }

    public void setTanggal_berakhir(String tanggal_berakhir) {
        this.tanggal_berakhir = tanggal_berakhir;
    }

    public String getTanggal_berlaku() {
        return tanggal_berlaku;
    }

    public void setTanggal_berlaku(String tanggal_berlaku) {
        this.tanggal_berlaku = tanggal_berlaku;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }
}
