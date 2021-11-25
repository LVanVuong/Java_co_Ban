package com.example.java_co_ban.Anime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java_co_ban.Anime.model.ItemFilm;
import com.example.java_co_ban.R;

import java.util.ArrayList;
import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder> {

    private List<ItemFilm> mListFiml;
    private List<ItemFilm> mListFilmOlm;
    private Clickfim mclickfim;

    public FilmAdapter(List<ItemFilm> mlistfiml, Clickfim clickfim) {
        this.mListFiml = mlistfiml;
        this.mListFilmOlm = mlistfiml;
        this.mclickfim = clickfim;
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewfilm = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemfilm, null, false);
        return new FilmViewHolder(viewfilm);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        final ItemFilm imagefilm = mListFiml.get(position);
        int localPosition = position;
        if (imagefilm == null) {
            return;
        }
        holder.imgFilm.setImageResource(imagefilm.getIteamfilm());
       holder.nameFilm.setText(imagefilm.getNamefilm());
       holder.tacgia.setText(imagefilm.getTacgia());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mclickfim.Clickitemfilm(imagefilm);
                mclickfim.onClickedVideoPosition(localPosition);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mListFiml != null) {
            return mListFiml.size();
        }
        return 0;
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout relativeLayout;
        private ImageView imgFilm;
        private TextView nameFilm;
        private TextView tacgia;

        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.layout_itemfilm);
            imgFilm = itemView.findViewById(R.id.Anime_Emilia);
           nameFilm = itemView.findViewById(R.id.TheloaiFilm);
            tacgia = itemView.findViewById(R.id.Tacgia);
        }
    }


    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String mkeySearch = constraint.toString();

                if (mkeySearch.isEmpty()) {
                    mListFiml = mListFilmOlm;
                } else {
                    List<ItemFilm> list = new ArrayList<>();
                    for (ItemFilm namefilm : mListFilmOlm) {
                        if (namefilm.getNamefilm().toLowerCase().contains(mkeySearch.toLowerCase())) {

                            list.add(namefilm);
                        }
                    }
                    mListFiml = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListFiml;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListFiml = (List<ItemFilm>) results.values;
                notifyDataSetChanged();

            }
        };
    }

}
