package com.example.java_co_ban.Anime;

import static com.example.java_co_ban.Notification.NotificationUtil.ID;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.Menu;

import com.example.java_co_ban.Anime.model.ItemFilm;
import com.example.java_co_ban.Anime.model.ResItemFilm;
import com.example.java_co_ban.PlayFilm.PlayFilmActivity;
import com.example.java_co_ban.R;
import com.example.java_co_ban.SearchDislay.Item;

import java.util.ArrayList;
import java.util.List;

public class AnimeActivity extends AppCompatActivity {

    private RecyclerView rcfilm;
    private FilmAdapter filmAdapter;
    private SearchView searchFilmView;
    private ResItemFilm mResItemFilm = new ResItemFilm();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime);

        Item item = (Item) getIntent().getSerializableExtra("key");


        rcfilm = findViewById(R.id.Emilia);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        rcfilm.setLayoutManager(gridLayoutManager);
        mResItemFilm.setItemFilms(getListFilm(item.getIdCateFilm()));
        filmAdapter = new FilmAdapter(getListFilm(item.getIdCateFilm()), new Clickfim() {
            @Override
            public void Clickitemfilm(ItemFilm itemFilm) {
                //Onclick(itemFilm);
            }

            @Override
            public void onClickedVideoPosition(int position) {
                Onclick(position);
            }
        });
        rcfilm.setAdapter(filmAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcfilm.addItemDecoration(itemDecoration);


    }

    private List<ItemFilm> getListFilm(long id) {
        List<ItemFilm> listfilm = new ArrayList<>();
        if (id == 1) {
            listfilm.add(new ItemFilm(R.drawable.emilia, "Emilia", 11, "https://0373566302.000webhostapp.com/linkfilm/1denhat.mp4","tocuda"));
            listfilm.add(new ItemFilm(R.drawable.img_1, "King Kong", 12, "https://0373566302.000webhostapp.com/linkfilm/tbh.mp4","tocuda"));
            listfilm.add(new ItemFilm(R.drawable.img_2, "Cat", 13, "","tocuda"));
            listfilm.add(new ItemFilm(R.drawable.img_3, "Dog", 14, "","tocuda"));
            listfilm.add(new ItemFilm(R.drawable.img_4, "Tiger", 15, "","tocuda"));
            listfilm.add(new ItemFilm(R.drawable.img_5, "Pig", 16, "","tocuda"));
            listfilm.add(new ItemFilm(R.drawable.img_6, "Chicken", 17, "","tocuda"));
            listfilm.add(new ItemFilm(R.drawable.img_7, "Snake", 18, "","tocuda"));
            listfilm.add(new ItemFilm(R.drawable.img_8, "Dragon", 19, "","tocuda"));
            listfilm.add(new ItemFilm(R.drawable.img_9, "Buffalo", 20, "","tocuda"));
        } else if (id == 2) {
            listfilm.add(new ItemFilm(R.drawable.img_6, "Chicken", 17, "","tocuda"));
            listfilm.add(new ItemFilm(R.drawable.img_7, "Snake", 18, "","tocuda"));
            listfilm.add(new ItemFilm(R.drawable.img_8, "Dragon", 19, "","tocuda"));
            listfilm.add(new ItemFilm(R.drawable.img_9, "Buffalo", 20, "","tocuda"));
        } else {
            listfilm.add(new ItemFilm(R.drawable.emilia, "Emilia", 11, "","tocuda"));
            listfilm.add(new ItemFilm(R.drawable.img_1, "King Kong", 12, "","tocuda"));
            listfilm.add(new ItemFilm(R.drawable.img_2, "Cat", 13, "","tocuda"));
            listfilm.add(new ItemFilm(R.drawable.img_3, "Dog", 14, "","tocuda"));
            listfilm.add(new ItemFilm(R.drawable.img_4, "Tiger", 15, "","tocuda"));
        }

        return listfilm;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menufilm, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchFilmView = (SearchView) menu.findItem(R.id.action_search1).getActionView();
        searchFilmView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchFilmView.setMaxWidth(Integer.MAX_VALUE);

        searchFilmView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filmAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filmAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
    @Override
    public void onBackPressed() {
        if (!searchFilmView.isIconified()) {
            searchFilmView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void Onclick(int position) {
        Intent intent = new Intent(this, PlayFilmActivity.class);
        mResItemFilm.setSelectedPosition(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", mResItemFilm);
        intent.putExtras(bundle);
        startActivity(intent);
    }

//    // Todo : Notification
//
//    private void SendNotificationMedia(){
//
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.backgroud);
//
//        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(this,"tag");
//
//        Notification notification = new NotificationCompat.Builder(this,ID)
//                .setSmallIcon(R.drawable.haha)
//                .setSubText("Emilia")
//                .setContentTitle("Title of Film")
//                .setContentText("Single of Film")
//                .setLargeIcon(bitmap)
//                .addAction(R.drawable.previous,"previois",null)
//                .addAction(R.drawable.play,"play",null)
//                .addAction(R.drawable.next,"next",null)
//                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
//                        .setShowActionsInCompactView(1 /* #1: pause button \*/)
//                        .setMediaSession(mediaSessionCompat.getSessionToken()))
//                .setContentTitle("Wonderful music")
//                .build();
//        NotificationManagerCompat notificationCompat = NotificationManagerCompat.from(this);
//        notificationCompat.notify(1,notification);
//
//    }

}