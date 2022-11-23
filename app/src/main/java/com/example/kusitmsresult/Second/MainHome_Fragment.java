package com.example.kusitmsresult.Second;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.kusitmsresult.R;

import java.util.ArrayList;
import java.util.List;


public class MainHome_Fragment extends Fragment {
    public MainHome_Fragment() {

    }
    public static MainHome_Fragment newInstance() {
        return new MainHome_Fragment ();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_main_home, container, false);
        ImageSlider imageSlider = rootview.findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel("https://img7.yna.co.kr/etc/inner/KR/2020/11/13/AKR20201113124100054_01_i_P4.jpg", ""));
        imageSlider.setImageList(slideModels, true);

        return rootview;
    }
}