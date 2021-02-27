package com.example.pro_desa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Role {
    public final static String ROLE_SUPER_ADMIN = "superadmin";
    public final static String ROLE_ADMIN_NASIONAL = "admin nasional";
    public final static String ROLE_ADMIN_REGIONAL = "admin rasional";
    public final static String ROLE_KEPALA_DESA = "kepala desa";
    public final static String ROLE_KEPALA_DUSUN = "superadmin";
    public final static String ROLE_RT = "rt";
    public final static String ROLE_RW = "rw";
    public final static String ROLE_MASYARAKAT = "masyarakat";

    @Expose
    @SerializedName("id") int id;
    @Expose
    @SerializedName("name") String name;

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
}
