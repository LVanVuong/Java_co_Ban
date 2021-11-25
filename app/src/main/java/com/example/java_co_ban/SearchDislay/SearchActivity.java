package com.example.java_co_ban.SearchDislay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.example.java_co_ban.Anime.AnimeActivity;
import com.example.java_co_ban.R;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView rcImage;
    private ImageAdapter imageAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        rcImage = findViewById(R.id.Image);

        // Cach hien thi listview
        /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ThreeActivity.this);
        rcImage.setLayoutManager(linearLayoutManager);*/

        // Cach hien thi gridview
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        rcImage.setLayoutManager(gridLayoutManager);

        imageAdapter = new ImageAdapter(getListImage(), new IClick() {
            @Override
            public void Clickitem(Item item) {
                Onclick(item);
            }
        });
        rcImage.setAdapter(imageAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcImage.addItemDecoration(itemDecoration);
    }


    private List<Item> getListImage() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(R.drawable.enime, "Anime", "anime", 1));
        list.add(new Item(R.drawable.hanhdong, "Phim Hành Động", "hành động", 2));
        list.add(new Item(R.drawable.chientanh, "Phim Chiến Tranh", "chiến tranh", 3));
        list.add(new Item(R.drawable.thethao, "Phim Thể Thao", "thể thao", 4));
        list.add(new Item(R.drawable.cotrang, "Phim Cổ Trang", "cổ trang", 5));
        list.add(new Item(R.drawable.hinhsu, "Phim Hình Sự", "hình sự", 6));
        list.add(new Item(R.drawable.tinhcam, "Phim Tình cảm", "tình cảm", 7));
        list.add(new Item(R.drawable.hoathinh, "Phim Hoạt Hình", "hoạt hình", 8));
        list.add(new Item(R.drawable.khoahocvt, "Phim Khoa Học Viễn Tưỡng", "khoa học viễn tưởng", 9));
        list.add(new Item(R.drawable.vothuat, "Phim Võ Thuật", "võ thuật", 10));
        list.add(new Item(R.drawable.kinhdi, "Phim Kinh Dị", "kinh dị", 11));


        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_memu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                imageAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                imageAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void Onclick(Item item) {
        Intent intent = new Intent(this, AnimeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", item);
        intent.putExtras(bundle);
        startActivity(intent);

//        NotificationData notiData = new NotificationData();
//        notiData.setContent(item.getAddress());
//        notiData.setTitle(item.getName());
//        notiData.setContentFull(item.getAddress());
//        notiData.setTitleFull(item.getName());
//        notiData.setUrlFilm("https://0373566302.000webhostapp.com/linkfilm/1denhat.mp4");
//        NotificationUtil.showNotification(this, notiData);
    }
}
