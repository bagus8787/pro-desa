package com.example.pro_desa.network.response.file_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PermohonanFileResponse {
    @Expose
    @SerializedName("code") int code;
    @Expose
    @SerializedName("message") String message;
    @Expose
    @SerializedName("data")
    FileResponse fileResponse;

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

    public FileResponse getFileResponse() {
        return fileResponse;
    }

    public void setFileResponse(FileResponse fileResponse) {
        this.fileResponse = fileResponse;
    }
}
