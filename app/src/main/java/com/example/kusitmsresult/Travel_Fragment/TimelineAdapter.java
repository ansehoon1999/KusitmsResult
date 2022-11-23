package com.example.kusitmsresult.Travel_Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kusitmsresult.R;
import com.example.kusitmsresult.model.Evaluation2_ListItem;
import com.repsly.library.timelineview.LineType;
import com.repsly.library.timelineview.TimelineView;

import java.util.List;

class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;
    private final int orientation;
    private final List<Evaluation2_ListItem> items;

    TimelineAdapter(int orientation, List<Evaluation2_ListItem> items, OnItemClickListener onItemClickListener) {
        this.orientation = orientation;
        this.items = items;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (orientation == LinearLayoutManager.VERTICAL) {
            return R.layout.item_vertical;
        } else {
            return R.layout.item_horizontal;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.evaluation_item_route.setText(items.get(position).getRoute());
        holder.timelineView.setLineType(getLineType(position));
        holder.timelineView.setNumber(position);
        holder.buttonInstead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private LineType getLineType(int position) {
        if (getItemCount() == 1) {
            return LineType.ONLYONE;

        } else {
            if (position == 0) {
                return LineType.BEGIN;

            } else if (position == getItemCount() - 1) {
                return LineType.END;

            } else {
                return LineType.NORMAL;
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TimelineView timelineView;
        TextView evaluation_item_route;
        LinearLayout buttonInstead;


        ViewHolder(View view) {
            super(view);
            timelineView = (TimelineView) view.findViewById(R.id.timeline);
            evaluation_item_route = (TextView) view.findViewById(R.id.evaluation_item_route);
            buttonInstead = view.findViewById(R.id.buttonInstead);
        }
    }

}