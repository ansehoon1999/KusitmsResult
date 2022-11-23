package com.example.kusitmsresult.Travel_Fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kusitmsresult.R;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity {
    private MaterialCalendarView Calendar_calendarView;
    private Button Calendar_nextButton;
    private String Calendar_selectedDate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        init();


            Calendar_nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(Calendar_calendarView.getSelectedDate() == null) {
                        Toast.makeText(getApplicationContext(), "날짜를 선택해주세요", Toast.LENGTH_SHORT).show();
                    }

                    else {

                        Calendar_selectedDate = String.valueOf(Calendar_calendarView.getSelectedDate()).substring(12);
                        Calendar_selectedDate = Calendar_selectedDate.replace("}", "");

                        Toast.makeText(CalendarActivity.this, Calendar_selectedDate, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), Gone1Activity.class);
                        intent.putExtra("Calendar_selectedDate", Calendar_selectedDate);
                        intent.putExtra("date", Calendar_selectedDate);
                        startActivity(intent);
                    }
                }
            });
    }

    private void init() {
        Calendar_calendarView = findViewById(R.id.Calendar_calendarView);
        Calendar_nextButton = findViewById(R.id.Calendar_nextButton);
    }
}