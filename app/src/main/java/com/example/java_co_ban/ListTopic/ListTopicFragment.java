package com.example.java_co_ban.ListTopic;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.java_co_ban.R;
import com.example.java_co_ban.Sever.APIService;
import com.example.java_co_ban.Sever.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTopicFragment extends Fragment {
    private RecyclerView rcImage;
    private ListTopicAdapter listTopicAdapter;
    private SearchView searchView;
    private static  ArrayList<Theloai> topicArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_list_topic, container, false);
        rcImage = root.findViewById(R.id.Imageview);
        // Cach hien thi gridview
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        //rcImage.setLayoutManager(gridLayoutManager);

        getListTheloai();
        setHasOptionsMenu(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rcImage.addItemDecoration(itemDecoration);
        return root;
    }

    private void getListTheloai() {
        Dataservice dataServive = APIService.getService();
        Call<List<Theloai>> call = dataServive.GetTheloai();
        call.enqueue(new Callback<List<Theloai>>() {
            @Override
            public void onResponse(Call<List<Theloai>> call, Response<List<Theloai>> response) {
                topicArrayList =(ArrayList<Theloai>) response.body();
                listTopicAdapter = new ListTopicAdapter(getActivity(),topicArrayList);
                rcImage.setLayoutManager(new LinearLayoutManager(getActivity()));
                rcImage.setAdapter(listTopicAdapter);
            }

            @Override
            public void onFailure(Call<List<Theloai>> call, Throwable t) {
                Log.i("vvvvvvvvvvvvv","Khong lay duoc data, "+t.getMessage());
            }


        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.main_memu, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listTopicAdapter.getFilter().filter((query));
               return  false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listTopicAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}