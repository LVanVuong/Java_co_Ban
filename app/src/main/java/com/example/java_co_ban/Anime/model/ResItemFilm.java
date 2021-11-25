package com.example.java_co_ban.Anime.model;

import java.io.Serializable;
import java.util.List;

public class ResItemFilm implements Serializable {

    private List<ItemFilm> itemFilms;
    private int selectedPosition;

    public List<ItemFilm> getItemFilms() {
        return itemFilms;
    }

    public void setItemFilms(List<ItemFilm> itemFilms) {
        this.itemFilms = itemFilms;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
}
