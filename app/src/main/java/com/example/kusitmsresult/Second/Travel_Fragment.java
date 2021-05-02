package com.example.kusitmsresult.Second;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kusitmsresult.R;
import com.example.kusitmsresult.Travel_Fragment.CalendarActivity;
import com.example.kusitmsresult.Travel_Fragment.StartTravelActivity;

public class Travel_Fragment extends Fragment implements View.OnClickListener {

    private Button travel_writeButton;
    private Button travel_startButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_travel, container, false);

        travel_writeButton = rootview.findViewById(R.id.travel_writeButton);
        travel_startButton = rootview.findViewById(R.id.travel_startButton);

        travel_writeButton.setOnClickListener(this);
        travel_startButton.setOnClickListener(this);

        return rootview;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.travel_writeButton :
                Intent intent1 = new Intent(getActivity(), CalendarActivity.class);
                startActivity(intent1);
                break;
            case R.id.travel_startButton :
                Intent intent2 = new Intent(getActivity(), StartTravelActivity.class);
                startActivity(intent2);
                break;
        }
    }
}