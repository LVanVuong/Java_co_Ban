package com.example.java_co_ban.SearchDislay;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java_co_ban.ListFilm.FilmActivity;
import com.example.java_co_ban.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListTopicAdapter extends RecyclerView.Adapter<ListTopicAdapter.ViewHolder>{

    Context context;
    ArrayList<Theloai> theLoaiArrayList;
    ArrayList<Theloai> mtheLoaiArrayList;

    public ListTopicAdapter(Context context ,ArrayList<Theloai> theLoaiArrayList ) {
        this.context = context;
        this.theLoaiArrayList = theLoaiArrayList;
        this.mtheLoaiArrayList = theLoaiArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_list_topic,parent,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull ListTopicAdapter.ViewHolder holder, int position) {
        Theloai theLoai = theLoaiArrayList.get(position);
        Picasso.with(context).load(theLoai.getHinhtheloai()).into(holder.imghinnen);
        holder.txtViewtentheloai.setText(theLoai.getTentheloai());
        holder.tentheloai.setText(theLoai.getTentheloai());
    }

    @Override
    public int getItemCount() {
        if (theLoaiArrayList != null) {
            return theLoaiArrayList.size();
        }
        return 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imghinnen;
        TextView txtViewtentheloai,tentheloai;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imghinnen = itemView.findViewById(R.id.Imagetheloai);
            txtViewtentheloai = itemView.findViewById(R.id.Tentheloai);
            tentheloai = itemView.findViewById(R.id.tentheloai);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FilmActivity.class);
                    intent.putExtra("idtheloai",theLoaiArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }


    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String mkeySearch = constraint.toString();

                if (mkeySearch.isEmpty()) {
                    theLoaiArrayList = mtheLoaiArrayList;
                } else {
                    List<Theloai> list = new ArrayList<>();
                    for (Theloai theloai : mtheLoaiArrayList) {
                        if (theloai.getTentheloai().toLowerCase().contains(mkeySearch.toLowerCase()))
                        {

                            list.add(theloai);
                        }
                    }
                    theLoaiArrayList = (ArrayList<Theloai>) list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = theLoaiArrayList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                theLoaiArrayList = (ArrayList<Theloai>) results.values;
                notifyDataSetChanged();

            }
        };
    }
}

