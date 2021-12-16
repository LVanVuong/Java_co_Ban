package com.example.java_co_ban.SearchDislay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

//import com.example.java_co_ban.Anime.AnimeActivity;
import com.example.java_co_ban.ListFilm.FilmActivity;
import com.example.java_co_ban.R;
import com.example.java_co_ban.Sever.APIService;
import com.example.java_co_ban.Sever.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTopicActivity extends AppCompatActivity {
    private RecyclerView rcImage;
    private ListTopicAdapter listTopicAdapter;
    private SearchView searchView;
    private static  ArrayList<Theloai> topicArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_topic);
        rcImage = findViewById(R.id.Imageview);
        // Cach hien thi gridview
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        rcImage.setLayoutManager(gridLayoutManager);
        //if(theloai != null && !theloai.getTentheloai().equals("")){
            getListTheloai();
        //}

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcImage.addItemDecoration(itemDecoration);
    }

    private void getListTheloai() {
        Dataservice dataServive = APIService.getService();
        Call<List<Theloai>> call = dataServive.GetTheloai();
        call.enqueue(new Callback<List<Theloai>>() {
            @Override
            public void onResponse(Call<List<Theloai>> call, Response<List<Theloai>> response) {
                topicArrayList =(ArrayList<Theloai>) response.body();
                listTopicAdapter = new ListTopicAdapter(ListTopicActivity.this,topicArrayList);
                rcImage.setLayoutManager(new LinearLayoutManager(ListTopicActivity.this));
                rcImage.setAdapter(listTopicAdapter);

            }

            @Override
            public void onFailure(Call<List<Theloai>> call, Throwable t) {

            }


        });

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
               listTopicAdapter.getFilter().filter((query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listTopicAdapter.getFilter().filter(newText);
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

    private void Onclick(Theloai theloai) {
        Intent intent = new Intent(this, FilmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", theloai);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}