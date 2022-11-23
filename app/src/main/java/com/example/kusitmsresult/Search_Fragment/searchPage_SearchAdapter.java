package com.example.kusitmsresult.Search_Fragment;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kusitmsresult.R;
import com.example.kusitmsresult.model.AboveModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class searchPage_SearchAdapter extends RecyclerView.Adapter<searchPage_SearchAdapter.CustomViewHolder> {

    public boolean isShimmer = true;
    public int shimmerNumber = 5;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;
    private ArrayList<AboveModel> arrayList; //아까 만든 class에서의 User
    private Context context;





    public searchPage_SearchAdapter(ArrayList<AboveModel> arrayList, Context context, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scrap_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    } //adapter에 연결이 되고난 후 viewholder를 최초로 만들어 낸다.

    @Override
    public void onBindViewHolder(@NonNull final searchPage_SearchAdapter.CustomViewHolder holder, final int position) {


        if (isShimmer) {
            holder.shimmerFrameLayout.startShimmer();
        } else {

            holder.shimmerFrameLayout.stopShimmer();
            holder.shimmerFrameLayout.setShimmer(null);
            holder.scrap_travelSite.setBackground(null);

            holder.scrap_profile.setBackground(null);
            holder.scrap_travelDate.setBackground(null);

            String[] pathArr1 = arrayList.get(position).uri.split(",");
            String button_string = arrayList.get(position).buttonArray;
            String[] travelDateArr = arrayList.get(position).date.split("@");

            Glide.with(holder.itemView)
                    .load(pathArr1[0])
                    .apply(new RequestOptions().circleCrop())
                    .into(holder.scrap_profile);


            if (button_string.contains("T")) {
                Glide.with(holder.itemView)
                        .load(R.drawable.t_text)
                        .apply(new RequestOptions().circleCrop())
                        .into(holder.badge1);
            } else {
                Glide.with(holder.itemView)
                        .load(R.drawable.t_gray)
                        .apply(new RequestOptions().circleCrop())
                        .into(holder.badge1);
            }


            if (button_string.contains("R")) {
                Glide.with(holder.itemView)
                        .load(R.drawable.r_text)
                        .apply(new RequestOptions().circleCrop())
                        .into(holder.badge2);
            } else {
                Glide.with(holder.itemView)
                        .load(R.drawable.r_gray)
                        .apply(new RequestOptions().circleCrop())
                        .into(holder.badge2);
            }


            if (button_string.contains("I")) {
                Glide.with(holder.itemView)
                        .load(R.drawable.i_text)
                        .apply(new RequestOptions().circleCrop())
                        .into(holder.badge3);
            } else {
                Glide.with(holder.itemView)
                        .load(R.drawable.i_gray)
                        .apply(new RequestOptions().circleCrop())
                        .into(holder.badge3);
            }
            if (button_string.contains("P")) {
                Glide.with(holder.itemView)
                        .load(R.drawable.p_text)
                        .apply(new RequestOptions().circleCrop())
                        .into(holder.badge4);
            } else {
                Glide.with(holder.itemView)
                        .load(R.drawable.p_gray)
                        .apply(new RequestOptions().circleCrop())
                        .into(holder.badge4);
            }


            holder.scrap_travelSite.setText(arrayList.get(position).path.replace("@", "/"));
            holder.scrap_travelDate.setText(travelDateArr[0]);
            holder.scrap_nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //삼항 연산자
        return isShimmer? shimmerNumber:arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder { //listview에서 만든 것들을 여기로 불러 놓을 거임


        ShimmerFrameLayout shimmerFrameLayout;

        ImageView scrap_profile;
        TextView scrap_travelSite;
        TextView scrap_travelDate;
        Button scrap_nextButton;

        ImageView badge1;
        ImageView badge2;
        ImageView badge3;
        ImageView badge4;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView); //view 에서 상속을 받았기 때문에 itemView에서 찾아야한다
            this.shimmerFrameLayout = itemView.findViewById(R.id.shimmerFrameLayout);

            this.scrap_travelSite = itemView.findViewById(R.id.scrap_travelSite);
            this.scrap_travelDate = itemView.findViewById(R.id.scrap_travelDate);
            this.scrap_nextButton = itemView.findViewById(R.id.scrap_nextButton);
            this.scrap_profile = itemView.findViewById(R.id.scrap_profile);

            this.badge1 = itemView.findViewById(R.id.badge1);
            this.badge2 = itemView.findViewById(R.id.badge2);
            this.badge3 = itemView.findViewById(R.id.badge3);
            this.badge4 = itemView.findViewById(R.id.badge4);



        }
    }
}