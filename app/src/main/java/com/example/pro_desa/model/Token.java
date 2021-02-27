package com.example.pro_desa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {
    @Expose
    @SerializedName("app_token") String app_token;
    @Expose
    @SerializedName("prodesa_token") String prodesa_token;

    public String getApp_token() {
        return app_token;
    }

    public void setApp_token(String app_token) {
        this.app_token = app_token;
    }

    public String getProdesa_token() {
        return prodesa_token;
    }

    public void setProdesa_token(String prodesa_token) {
        this.prodesa_token = prodesa_token;
    }
}
