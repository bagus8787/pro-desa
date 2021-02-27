package com.example.pro_desa.network.response;

import com.example.pro_desa.model.Token;
import com.example.pro_desa.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse extends BaseResponse {
    @Expose
    @SerializedName("data")
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
