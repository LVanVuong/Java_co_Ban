package com.example.java_co_ban.ListFilm;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.example.java_co_ban.PlayFilm.PlayFilmActivity;
import com.example.java_co_ban.R;

import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;

import com.example.java_co_ban.SearchDislay.Theloai;
import com.example.java_co_ban.Sever.APIService;
import com.example.java_co_ban.Sever.Dataservice;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton floatingActionButton;
    CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;
    Theloai theloai;
    ImageView view;


    public static ArrayList<Film> filmArrayList;
    ListFilmAdapter listFilmAdapter;
    RecyclerView recyclerViewfilm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        DataIntent();
        anhxa();

            floatingActionButton.setEnabled(true);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FilmActivity.this,PlayFilmActivity.class);
                    intent.putExtra("mfilm",filmArrayList);
                    startActivity(intent);
                }
            });

        if(theloai != null && !theloai.getTentheloai().equals("")){
            setValueInView(theloai.getTentheloai(),theloai.getHinhtheloai());
            GetDataTheLoai(theloai.getIdthloai());
        }
    }
    private void setValueInView(String ten, String hinh) {
        collapsingToolbarLayout.setTitle(ten);

        try {
            URL url = new URL(hinh);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(),bitmap);
            collapsingToolbarLayout.setBackground(bitmapDrawable);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
      //  Picasso.with(this).load(hinh).into(view);
    }

    private void GetDataTheLoai (String idtheloai) {
        Dataservice dataServive = APIService.getService();
        Call<List<Film>> call = dataServive.GetFilm(idtheloai);
        call.enqueue(new Callback<List<Film>>() {
            @Override
            public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {

                filmArrayList =(ArrayList<Film>) response.body();
                listFilmAdapter = new ListFilmAdapter(FilmActivity.this,filmArrayList);
                recyclerViewfilm.setLayoutManager(new LinearLayoutManager(FilmActivity.this));
                recyclerViewfilm.setAdapter(listFilmAdapter);

            }

            @Override
            public void onFailure(Call<List<Film>> call, Throwable t) {

            }


        });
    }
    private void DataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("idtheloai")) {
                theloai = (Theloai) intent.getSerializableExtra("idtheloai");
            }
        }
    }
    private void anhxa() {
        floatingActionButton = findViewById(R.id.floatingbtn);
        coordinatorLayout = findViewById(R.id.coordinatorlayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar);
        toolbar = findViewById(R.id.toolbarfilm);
        recyclerViewfilm = findViewById(R.id.listfilm);

    }



}
