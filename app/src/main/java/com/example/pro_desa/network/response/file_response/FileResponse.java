package com.example.pro_desa.network.response.file_response;

import com.example.pro_desa.model.ListFile;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FileResponse extends PermohonanFileResponse{
    @Expose
    @SerializedName("permohonan")
    ArrayList<ListFile> listFiles;

    public ArrayList<ListFile> getListFiles() {
        return listFiles;
    }

    public void setListFiles(ArrayList<ListFile> listFiles) {
        this.listFiles = listFiles;
    }
}
