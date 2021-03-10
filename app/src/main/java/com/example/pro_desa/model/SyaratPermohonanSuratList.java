package com.example.pro_desa.model;

import com.example.pro_desa.network.response.SyaratPermohonanSuratResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SyaratPermohonanSuratList extends SyaratPermohonanSuratResponse {
    @Expose
    @SerializedName("id") int id;
    @Expose
    @SerializedName("surat_format_id") int surat_format_id;
    @Expose
    @SerializedName("ref_syarat_id") int ref_syarat_id;
    @Expose
    @SerializedName("ref_syarat_nama") String ref_syarat_nama;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSurat_format_id() {
        return surat_format_id;
    }

    public void setSurat_format_id(int surat_format_id) {
        this.surat_format_id = surat_format_id;
    }

    public int getRef_syarat_id() {
        return ref_syarat_id;
    }

    public void setRef_syarat_id(int ref_syarat_id) {
        this.ref_syarat_id = ref_syarat_id;
    }

    public String getRef_syarat_nama() {
        return ref_syarat_nama;
    }

    public void setRef_syarat_nama(String ref_syarat_nama) {
        this.ref_syarat_nama = ref_syarat_nama;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SyaratPermohonanSuratList){
            SyaratPermohonanSuratList c = (SyaratPermohonanSuratList ) obj;
            if(c.getId()==id )
                return true;
        }

        return false;
    }
}
