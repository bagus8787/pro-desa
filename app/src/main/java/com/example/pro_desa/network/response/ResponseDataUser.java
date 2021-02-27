package com.example.pro_desa.network.response;

import com.example.pro_desa.model.AppData;
import com.example.pro_desa.model.Token;
import com.example.pro_desa.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseDataUser extends ResponseDataAwal{
    @Expose
    @SerializedName("user")
    User userData;

    @Expose
    @SerializedName("appData")
    AppData appData;

    public User getUserData() {
        return userData;
    }

    public void setUserData(User userData) {
        this.userData = userData;
    }

    public AppData getAppData() {
        return appData;
    }

    public void setAppData(AppData appData) {
        this.appData = appData;
    }
}
