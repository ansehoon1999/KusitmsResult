package com.example.kusitmsresult.Scrap_Fragment;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kusitmsresult.R;
import com.example.kusitmsresult.model.Personal_Information;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class scrapPage_CardStackAdapter extends RecyclerView.Adapter<scrapPage_CardStackAdapter.ViewHolder> {

    private List<Personal_Information> items;

    public scrapPage_CardStackAdapter(List<Personal_Information> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.searchpage_item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nama, usia, kota;
        ImageView imageView;
        ImageView badge1, badge2, badge3, badge4;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.item_image);

            nama = itemView.findViewById(R.id.item_name);
            usia = itemView.findViewById(R.id.item_age);
            kota = itemView.findViewById(R.id.item_city);

            badge1 = itemView.findViewById(R.id.badge1);
            badge2 = itemView.findViewById(R.id.badge2);
            badge3 = itemView.findViewById(R.id.badge3);
            badge4 = itemView.findViewById(R.id.badge4);

        }

        void setData(Personal_Information data) {

            Glide.with(itemView)
                    .load(data.image)
                    .into(imageView);


            if(data.LikeButton.contains("T")) {
                Glide.with(itemView)
                        .load(R.drawable.t_text)
                        .apply(new RequestOptions().circleCrop())
                        .into(badge1);
            } else {
                Glide.with(itemView)
                        .load(R.drawable.t_gray)
                        .apply(new RequestOptions().circleCrop())
                        .into(badge1);
            }

            if(data.LikeButton.contains("R")) {
                Glide.with(itemView)
                        .load(R.drawable.r_text)
                        .apply(new RequestOptions().circleCrop())
                        .into(badge2);
            }else {
                Glide.with(itemView)
                        .load(R.drawable.r_gray)
                        .apply(new RequestOptions().circleCrop())
                        .into(badge2);
            }
            if(data.LikeButton.contains("I")) {
                Glide.with(itemView)
                        .load(R.drawable.i_text)
                        .apply(new RequestOptions().circleCrop())
                        .into(badge3);
            }
            else {
                Glide.with(itemView)
                        .load(R.drawable.r_gray)
                        .apply(new RequestOptions().circleCrop())
                        .into(badge3);
            }


            if(data.LikeButton.contains("P")) {
                Glide.with(itemView)
                        .load(R.drawable.p_text)
                        .apply(new RequestOptions().circleCrop())
                        .into(badge4);
            }
            else {
                Glide.with(itemView)
                        .load(R.drawable.p_gray)
                        .apply(new RequestOptions().circleCrop())
                        .into(badge4);
            }




            nama.setText(data.route);
            usia.setText(data.rate);
            kota.setText(data.comment);
        }
    }

    public List<Personal_Information> getItems() {
        return items;
    }

    public void setItems(List<Personal_Information> items) {
        this.items = items;
    }
}