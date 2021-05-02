package com.example.kusitmsresult.First;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kusitmsresult.Second.MainActivity;
import com.example.kusitmsresult.R;

public class LoginActivity extends AppCompatActivity {
    Button Login_tmpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login_tmpButton = findViewById(R.id.Login_tmpButton);
        Login_tmpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

                // 이용약관 버튼 클릭시 이용약관 화면이 나와야함 지금은 MainActivity지만 바꿔야함
            }
        });


    }
}