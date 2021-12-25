package com.example.java_co_ban.Sever;

import com.example.java_co_ban.ListFilm.Film;
import com.example.java_co_ban.ListTopic.Theloai;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Dataservice {
//    @GET ("theloai.php")
//    Call<List<Theloai>> GetTheloai (@Field("IdTheLoai")String IdTheLoai);
    @GET ("theloai.php")
    Call<List<Theloai>> GetTheloai();

    @GET("film.php")
    Call<List<Film>> GetFilm (@Query("IdTheLoai") String idTheLoai);
}
