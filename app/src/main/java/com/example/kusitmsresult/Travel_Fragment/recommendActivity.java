package com.example.kusitmsresult.Travel_Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kusitmsresult.R;

import java.util.ArrayList;

public class recommendActivity extends AppCompatActivity {
    private ArrayList<String> rankList;
    private ListView listview;
    private ArrayAdapter<String> adapter;
    private String[] rank;
    Button button_ok;

    private String storename_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);


        rank = new String[3];

        //추천 장소 3가지


        Intent intent = getIntent();
        rankList = (ArrayList<String>) intent.getSerializableExtra("rankList");
        storename_selected = intent.getStringExtra("storename_selected");

        if(storename_selected.equals("덕수궁")) {
            rank[0] = "서울시립미술관";
            rank[1] = "피그인더가든";
            rank[2] = "어반가든";
            rankList.add(rank[0]);
            rankList.add(rank[1]);
            rankList.add(rank[2]);
        } else if(storename_selected.equals("르풀")) {
            rank[0] = "서울도서관";
            rank[1] = "루이키친";
            rank[2] = "국토발전전시관";
            rankList.add(rank[0]);
            rankList.add(rank[1]);
            rankList.add(rank[2]);
        } else if(storename_selected.equals("씨네큐브광화문")) {
            rank[0] = "교보문고 광화문";
            rank[1] = "숭례도담";
            rank[2] = "경복궁";
            rankList.add(rank[0]);
            rankList.add(rank[1]);
            rankList.add(rank[2]);
        }



//       for(int i=0;i<3; i++) {
//            rank[i] = rankList.get(i);
//        }

        button_ok = findViewById(R.id.recommend_button_ok);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listview = (ListView) findViewById(R.id.recommendActivity_listview);
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, rank);
//        listview.setAdapter(adapter);

        button_ok = (Button) findViewById(R.id.recommend_button_ok);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_selectable_list_item, rankList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };

        // DataBind ListView with items from ArrayAdapter
        listview.setAdapter(arrayAdapter);
    }
}