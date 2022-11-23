package com.example.kusitmsresult.Search_Fragment;

import androidx.recyclerview.widget.DiffUtil;

import com.example.kusitmsresult.model.Personal_Information;

import java.util.List;

public class CardStackCallback extends DiffUtil.Callback {

    private List<Personal_Information> old, baru;

    public CardStackCallback(List<Personal_Information> old, List<Personal_Information> baru) {
        this.old = old;
        this.baru = baru;
    }

    @Override
    public int getOldListSize() {
        return old.size();
    }

    @Override
    public int getNewListSize() {
        return baru.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition).getImage() == baru.get(newItemPosition).getImage();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition) == baru.get(newItemPosition);
    }
}