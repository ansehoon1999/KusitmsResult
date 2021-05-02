package com.example.kusitmsresult.Travel_Fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kusitmsresult.R;

public class StartTravelActivity extends AppCompatActivity  {
    Button Calendar_nextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_travel);

        Calendar_nextButton = findViewById(R.id.Calendar_nextButton);
        Calendar_nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


}