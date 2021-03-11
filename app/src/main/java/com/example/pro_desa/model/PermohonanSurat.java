package com.example.pro_desa.model;

import com.example.pro_desa.network.response.PermohonanSuratResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PermohonanSurat extends PermohonanSuratResponse {
    @Expose
    @SerializedName("id") int id;
    @Expose
    @SerializedName("id_pemohon") long id_pemohon;
    @Expose
    @SerializedName("id_surat") long id_surat;
    @Expose
    @SerializedName("isian_form") String isian_form;
    @Expose
    @SerializedName("status") int status;
    @Expose
    @SerializedName("keterangan") String keterangan;
    @Expose
    @SerializedName("no_hp_aktif") String no_hp_aktif;
    @Expose
    @SerializedName("syarat") String syarat;
    @Expose
    @SerializedName("alasan_pembatalan") String alasan_pembatalan;
    @Expose
    @SerializedName("nama_kategori_surat") String nama_kategori_surat;
    @Expose
    @SerializedName("status_permohonan") String status_permohonan;
    @Expose
    @SerializedName("created_at") String created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getId_pemohon() {
        return id_pemohon;
    }

    public void setId_pemohon(long id_pemohon) {
        this.id_pemohon = id_pemohon;
    }

    public long getId_surat() {
        return id_surat;
    }

    public void setId_surat(long id_surat) {
        this.id_surat = id_surat;
    }

    public String getIsian_form() {
        return isian_form;
    }

    public void setIsian_form(String isian_form) {
        this.isian_form = isian_form;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getNo_hp_aktif() {
        return no_hp_aktif;
    }

    public void setNo_hp_aktif(String no_hp_aktif) {
        this.no_hp_aktif = no_hp_aktif;
    }

    public String getSyarat() {
        return syarat;
    }

    public void setSyarat(String syarat) {
        this.syarat = syarat;
    }

    public String getAlasan_pembatalan() {
        return alasan_pembatalan;
    }

    public void setAlasan_pembatalan(String alasan_pembatalan) {
        this.alasan_pembatalan = alasan_pembatalan;
    }

    public String getNama_kategori_surat() {
        return nama_kategori_surat;
    }

    public void setNama_kategori_surat(String nama_kategori_surat) {
        this.nama_kategori_surat = nama_kategori_surat;
    }

    public String getStatus_permohonan() {
        return status_permohonan;
    }

    public void setStatus_permohonan(String status_permohonan) {
        this.status_permohonan = status_permohonan;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
