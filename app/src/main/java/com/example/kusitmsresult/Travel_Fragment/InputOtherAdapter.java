package com.example.kusitmsresult.Travel_Fragment;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kusitmsresult.R;
import com.example.kusitmsresult.model.InputOtherModel;


import java.util.ArrayList;

public class InputOtherAdapter extends RecyclerView.Adapter<InputOtherAdapter.ViewHolder> {


    private Context mcontext;
    private ArrayList<InputOtherModel> listViewItemList  = new ArrayList<InputOtherModel>(); //아까 만든 class에서의 User

    public InputOtherAdapter(ArrayList<InputOtherModel> lastArrayList, Context context) {
        listViewItemList = lastArrayList;
        mcontext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.input_other_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Glide.with(mcontext)
                .asBitmap()
                .load(listViewItemList.get(position).uri.get(0))
                .into(holder.InputOtherListItem_ImageView);

        holder.InputOtherListItem_place_textView.setText(listViewItemList.get(position).place);
        holder.InputOtherListItem_time_textView.setText(listViewItemList.get(position).time);
        holder.InputOtherListItem_rating_textView.setText(listViewItemList.get(position).rating);
        holder.InputOtherListItem_comment_textView.setText(listViewItemList.get(position).comment);

    }

    @Override
    public int getItemCount() {
        return listViewItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView InputOtherListItem_ImageView;
        TextView InputOtherListItem_place_textView;
        TextView InputOtherListItem_time_textView;
        TextView InputOtherListItem_rating_textView;
        TextView InputOtherListItem_comment_textView;

        public ViewHolder(View itemView) {
            super(itemView);
            InputOtherListItem_ImageView = itemView.findViewById(R.id.InputOtherListItem_ImageView);
            InputOtherListItem_place_textView = itemView.findViewById(R.id.InputOtherListItem_place_textView);
            InputOtherListItem_time_textView = itemView.findViewById(R.id.InputOtherListItem_time_textView);
            InputOtherListItem_rating_textView = itemView.findViewById(R.id.InputOtherListItem_rating_textView);
            InputOtherListItem_comment_textView = itemView.findViewById(R.id.InputOtherListItem_comment_textView);
        }
    }
}