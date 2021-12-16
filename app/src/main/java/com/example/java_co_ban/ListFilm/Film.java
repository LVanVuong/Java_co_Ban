package com.example.java_co_ban.ListFilm;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Film implements Parcelable {

    @SerializedName("idfilm")
    @Expose
    private String idfilm;
    @SerializedName("idtheloai")
    @Expose
    private String idtheloai;
    @SerializedName("tenfilm")
    @Expose
    private String tenfilm;
    @SerializedName("tacgia")
    @Expose
    private String tacgia;
    @SerializedName("hinhfilm")
    @Expose
    private String hinhfilm;
    @SerializedName("linkfilm")
    @Expose
    private String linkfilm;

    protected Film(Parcel in) {
        idfilm = in.readString();
        idtheloai = in.readString();
        tenfilm = in.readString();
        tacgia = in.readString();
        hinhfilm = in.readString();
        linkfilm = in.readString();
    }
    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

    public String getIdfilm() {
        return idfilm;
    }

    public void setIdfilm(String idfilm) {
        this.idfilm = idfilm;
    }

    public String getIdtheloai() {
        return idtheloai;
    }

    public void setIdtheloai(String idtheloai) {
        this.idtheloai = idtheloai;
    }

    public String getTenfilm() {
        return tenfilm;
    }

    public void setTenfilm(String tenfilm) {
        this.tenfilm = tenfilm;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public String getHinhfilm() {
        return hinhfilm;
    }

    public void setHinhfilm(String hinhfilm) {
        this.hinhfilm = hinhfilm;
    }

    public String getLinkfilm() {
        return linkfilm;
    }

    public void setLinkfilm(String linkfilm) {
        this.linkfilm = linkfilm;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idfilm);
        dest.writeString(idtheloai);
        dest.writeString(tenfilm);
        dest.writeString(tacgia);
        dest.writeString(hinhfilm);
        dest.writeString(linkfilm);
    }
}
