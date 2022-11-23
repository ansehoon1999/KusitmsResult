package com.example.kusitmsresult.Travel_Fragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kusitmsresult.R;
import com.example.kusitmsresult.model.Evaluation2_ListItem;
import com.example.kusitmsresult.model.EvaluationModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.transferwise.sequencelayout.SequenceStep;

import java.sql.Array;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class EvaluationActivity1 extends AppCompatActivity implements View.OnClickListener {
    public static final String ORIENTATION = "orientation";

    private Button evaluation_registerButton;
    private Button evaluation_tmpStoreButton;
    private Button evaluation_addButton;

    private String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private ArrayList<String> arraylist;

    public static final int evaluation1_registerButtonNum = 1;
    public static final int evaluation1_tmpStoreButtonNum = 2;
    public static final int evaluation1_addButtonNum = 3;

    private String secret_code;
    private String date;
    private String resultArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation1);

        init();

        arraylist = new ArrayList<String>();
        Intent intent = getIntent();
        arraylist = (ArrayList<String>) intent.getSerializableExtra("arrayList");


        for (int i = 0; i < arraylist.size(); i++) {
            if (i == 0) {
                resultArray = resultArray + arraylist.get(i);

            } else {
                resultArray = resultArray + "@" + arraylist.get(i);
            }
        }


        secret_code = myUid + resultArray;

        date = intent.getStringExtra("date");

        int orientation = getIntent().getIntExtra(ORIENTATION, LinearLayoutManager.VERTICAL);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, orientation, false));


        evaluation_registerButton.setOnClickListener(this);
        evaluation_tmpStoreButton.setOnClickListener(this);
        evaluation_addButton.setOnClickListener(this);



        TimelineAdapter adapter = new TimelineAdapter(orientation, getItems(), new TimelineAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), InputActivity.class);
                intent.putExtra("route", getItems().get(position).getRoute());
                intent.putExtra("date", getItems().get(position).getdate());
                intent.putExtra("secret_code", secret_code);

                startActivity(intent);
            }
        });
        recycler.setAdapter(adapter);



    }

    private void init() {
        evaluation_registerButton = findViewById(R.id.evaluation_registerButton);
        evaluation_tmpStoreButton = findViewById(R.id.evaluation_tmpStoreButton);
        evaluation_addButton = findViewById(R.id.evaluation_addButton);


        Intent intent = getIntent();
        String Calendar_selectedDate = intent.getStringExtra("Calendar_selectedDate");


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.evaluation_registerButton:

                registerButtonEvent();


                break;

            case R.id.evaluation_tmpStoreButton:
                tmpStoreButtonEvent();

                break;


            case R.id.evaluation_addButton:

                Intent intent = new Intent(getApplicationContext(), InputOtherActivity.class);
                intent.putExtra("route", "");
                intent.putExtra("date", "");
                intent.putExtra("time", "");
                intent.putExtra("secret_code", secret_code);
                startActivityForResult(intent, evaluation1_addButtonNum);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case evaluation1_addButtonNum :
                    arraylist.add(data.getStringExtra("result"));

                    Toast.makeText(this, "this" + arraylist.size(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    private List<Evaluation2_ListItem> getItems() {
        List<Evaluation2_ListItem> items = new ArrayList<>();

        for (int i = 0; i < arraylist.size(); i++) {
            Evaluation2_ListItem item = new Evaluation2_ListItem();
            item.setRoute(arraylist.get(i));
            item.setdate(date);
            item.settime("");
            items.add(item);
        }

        return items;
    }

    private void registerButtonEvent() {

        for(int i=0; i< arraylist.size(); i++) {
            FirebaseDatabase.getInstance().getReference().child("Personal_Information")
                    .child(secret_code).child(secret_code + arraylist.get(i)).child("state").setValue("true");
        }

        FirebaseDatabase.getInstance().getReference().child("Above_information")
                .child(secret_code).child("state").setValue("true");

        finish();


    }

    private void tmpStoreButtonEvent() {
        for(int i=0; i< arraylist.size(); i++) {
            FirebaseDatabase.getInstance().getReference().child("Personal_Information")
                    .child(secret_code).child(secret_code + arraylist.get(i)).child("state").setValue("tmp");
        }

        FirebaseDatabase.getInstance().getReference().child("Above_information")
                .child(secret_code).child("state").setValue("tmp");

        finish();
    }

}