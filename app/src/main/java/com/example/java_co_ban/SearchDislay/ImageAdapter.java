package com.example.java_co_ban.SearchDislay;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java_co_ban.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageView> implements Filterable {
    private List<Item> mListIteam;
    private List<Item> mListIteamOld;
    private IClick iClick;

    public ImageAdapter(List<Item> mlistIteam ,IClick iClick) {

        this.mListIteam = mlistIteam;
        this.mListIteamOld = mlistIteam;
        this.iClick = iClick;
    }

    @NonNull
    @Override
    public ImageView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_file, parent, false);
        return new ImageView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageView holder, int position) {
        final Item image = mListIteam.get(position);
        if (image == null) {
            return;
        }
        holder.imageview.setImageResource(image.getImage());
        holder.theloai.setText(image.getName());
        holder.address.setText(image.getAddress());

        holder.layoutitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClick.Clickitem(image);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mListIteam != null) {
            return mListIteam.size();
        }
        return 0;
    }


    public class ImageView extends RecyclerView.ViewHolder {

        private RelativeLayout layoutitem;
        private CircleImageView imageview;
        private TextView theloai;
        private TextView address;

        public ImageView(@NonNull View itemView) {
            super(itemView);

            layoutitem = itemView.findViewById(R.id.layout_item);
            imageview = itemView.findViewById(R.id.Anime);
            theloai = itemView.findViewById(R.id.Theloai);
            address = itemView.findViewById(R.id.Address);


        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String mkeySearch = constraint.toString();

                if (mkeySearch.isEmpty()) {
                    mListIteam = mListIteamOld;
                } else {
                    List<Item> list = new ArrayList<>();
                    for (Item theloai : mListIteamOld) {
                        if (theloai.getName().toLowerCase().contains(mkeySearch.toLowerCase()))
                        {

                            list.add(theloai);
                        }
                    }
                    mListIteam = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListIteam;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListIteam = (List<Item>) results.values;
                notifyDataSetChanged();

            }
        };
    }


}
