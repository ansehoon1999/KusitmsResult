package com.example.kusitmsresult.First;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kusitmsresult.R;

public class TutorialActivity extends AppCompatActivity {
    Button Tutorial_tmp_Button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);


        Tutorial_tmp_Button = findViewById(R.id.Tutorial_tmp_Button);
        Tutorial_tmp_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}