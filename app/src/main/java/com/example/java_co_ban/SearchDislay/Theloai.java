package com.example.java_co_ban.SearchDislay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Theloai implements Serializable {
    @SerializedName("idtheloai")
    @Expose
    private String idthloai;
    @SerializedName("tentheloai")
    @Expose
    private String tentheloai;
    @SerializedName("hinhtheloai")
    @Expose
    private String hinhtheloai;

    public String getIdthloai() {
        return idthloai;
    }

    public void setIdthloai(String idthloai) {
        this.idthloai = idthloai;
    }

    public String getTentheloai() {
        return tentheloai;
    }

    public void setTentheloai(String tentheloai) {
        this.tentheloai = tentheloai;
    }

    public String getHinhtheloai() {
        return hinhtheloai;
    }

    public void setHinhtheloai(String hinhtheloai) {
        this.hinhtheloai = hinhtheloai;
    }
}
