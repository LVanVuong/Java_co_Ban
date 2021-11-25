package com.example.java_co_ban.Anime.model;

import java.io.Serializable;

public class ItemFilm implements Serializable {
    private int iteamfilm;
    private String namefilm;
    private String linkFilm;
    private String tacgia;

    public int getIteamfilm() {
        return iteamfilm;
    }

    public void setIteamfilm(int iteamfilm) {
        this.iteamfilm = iteamfilm;
    }

    public String getNamefilm() {
        return namefilm;
    }

    public void setNamefilm(String namefilm) {
        this.namefilm = namefilm;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public ItemFilm(int iteamfilm, String namefilm, long keyFilm, String linkFilm,String tacgia){
        this.iteamfilm = iteamfilm;
        this.namefilm = namefilm;
        this.keyFilm = keyFilm;
        this.linkFilm = linkFilm;
        this.tacgia = tacgia;

    }

    public String getLinkFilm() {
        return linkFilm;
    }

    private long keyFilm;

    public long getkeyFilm() {
        return keyFilm;
    }

    public void setkeyFilm(long keyFilm) {
        this.keyFilm = keyFilm;
    }
}
