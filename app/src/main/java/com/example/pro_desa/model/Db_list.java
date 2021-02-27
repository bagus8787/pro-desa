package com.example.pro_desa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Db_list {
    @Expose
    @SerializedName("id") int id;
    @Expose
    @SerializedName("app_name") String name;
    @Expose
    @SerializedName("provinsi_id") long provinsi_id;
    @Expose
    @SerializedName("kota_id") long kota_id;
    @Expose
    @SerializedName("kecamatan_id") long kecamatan_id;
    @Expose
    @SerializedName("kelurahan_id") long kelurahan_id;
    @Expose
    @SerializedName("app_folder_name") String app_folder_name;
    @Expose
    @SerializedName("app_folder") String app_folder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApp_folder_name() {
        return app_folder_name;
    }

    public void setApp_folder_name(String app_folder_name) {
        this.app_folder_name = app_folder_name;
    }

    public String getApp_folder() {
        return app_folder;
    }

    public void setApp_folder(String app_folder) {
        this.app_folder = app_folder;
    }

    public long getProvinsi_id() {
        return provinsi_id;
    }

    public void setProvinsi_id(long provinsi_id) {
        this.provinsi_id = provinsi_id;
    }

    public long getKota_id() {
        return kota_id;
    }

    public void setKota_id(long kota_id) {
        this.kota_id = kota_id;
    }

    public long getKecamatan_id() {
        return kecamatan_id;
    }

    public void setKecamatan_id(long kecamatan_id) {
        this.kecamatan_id = kecamatan_id;
    }

    public long getKelurahan_id() {
        return kelurahan_id;
    }

    public void setKelurahan_id(long kelurahan_id) {
        this.kelurahan_id = kelurahan_id;
    }
}
