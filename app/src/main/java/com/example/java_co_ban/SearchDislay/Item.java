package com.example.java_co_ban.SearchDislay;

import java.io.Serializable;

public class Item implements Serializable {
    private int Image;

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String name;

    public Item(int image, String name, String address, long idCateFilm) {
        Image = image;
        this.name = name;
        this.address = address;
        this.idCateFilm = idCateFilm;
    }

    private String address;

    private long idCateFilm;

    public long getIdCateFilm() {
        return idCateFilm;
    }

    public void setIdCateFilm(long idCateFilm) {
        this.idCateFilm = idCateFilm;
    }
}
