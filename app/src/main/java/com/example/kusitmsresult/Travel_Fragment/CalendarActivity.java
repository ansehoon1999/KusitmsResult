package com.example.kusitmsresult.Travel_Fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kusitmsresult.R;

public class CalendarActivity extends AppCompatActivity {
    private Button startTravel_nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        startTravel_nextButton = findViewById(R.id.startTravel_nextButton);
        startTravel_nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EvaluationActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}