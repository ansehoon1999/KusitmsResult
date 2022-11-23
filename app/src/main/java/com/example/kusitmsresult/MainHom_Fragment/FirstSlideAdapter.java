package com.example.kusitmsresult.MainHom_Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.kusitmsresult.R;


public class FirstSlideAdapter extends PagerAdapter {
    private int[] images = {R.drawable.tmp,R.drawable.tmp, R.drawable.tmp };
    private LayoutInflater inflater;
    private Context context;

    public FirstSlideAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.page_slider1, container, false);
        ImageView imageView = view.findViewById(R.id.image);
        imageView.setImageResource(images[position]);
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.invalidate();
    }
}
