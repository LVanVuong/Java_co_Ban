package com.example.java_co_ban.ListFilm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java_co_ban.PlayFilm.PlayFilmActivity;
import com.example.java_co_ban.R;
import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

//import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public  class ListFilmAdapter extends RecyclerView.Adapter<ListFilmAdapter.ViewHolder>{
    Context context;
    public static ArrayList<Film> filmArrayList;

    public ListFilmAdapter(Context context, ArrayList<Film> filmArrayList) {
        this.context = context;
        this.filmArrayList = filmArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.listfilm,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ListFilmAdapter.ViewHolder holder, int position) {
        Film film =filmArrayList.get(position);
        holder.tentacgia.setText(film.getTacgia());
        holder.tenphim.setText(film.getTenfilm());
        Picasso.with(context).load(film.getHinhfilm()).into(holder.hinhphim);

    }

    @Override
    public int getItemCount() {
        return filmArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tentacgia,tenphim;
        ImageView hinhphim;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tentacgia = itemView.findViewById(R.id.tentacgia);
            tenphim = itemView.findViewById(R.id.tenfilm);
            hinhphim = itemView.findViewById(R.id.hinhfilm);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayFilmActivity.class);
                    intent.putExtra("film", filmArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }

    }

}

