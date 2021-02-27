package com.example.pro_desa.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    @Expose
    @SerializedName("code") int code;
    @Expose
    @SerializedName("message") String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public ResponseData getResponseData() {
//        return responseData;
//    }
//
//    public void setResponseData(ResponseData responseData) {
//        this.responseData = responseData;
//    }
}
