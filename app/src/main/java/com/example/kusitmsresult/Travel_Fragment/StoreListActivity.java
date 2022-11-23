package com.example.kusitmsresult.Travel_Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kusitmsresult.R;

import java.util.ArrayList;

public class StoreListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ArrayList<String> storename_list = new ArrayList<>(); //이전 intent에서 storename_list 받아오는 곳
    ListView listview;
    Button button_ok;
    Button button_cancel;
    String storename_selected;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);


        Bundle intent = getIntent().getExtras();
        storename_list = intent.getStringArrayList("storename_list");



        button_ok = (Button) findViewById(R.id.storeListActivity_button_ok);
        button_cancel = (Button) findViewById(R.id.storeListActivity_button_cancel);

        // 리소스 파일에 정의된 id_list 라는 ID 의 리스트뷰를 얻는다.
        listview = (ListView) findViewById(R.id.storeListActivity_listview);

        // ArrayAdapter 객체를 생성한다.
//        ArrayAdapter<String> adapter;

        // 리스트뷰가 보여질 아이템 리소스와 문자열 정보를 저장한다
        // 객체명 = new ArrayAdapter<데이터형>(참조할메소드, 보여질아이템리소스, 보여질문자열);
//        adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_selectable_list_item, storename_list);

        setListView();



        /*
         *
         * 안드로이드에서 제공하는 기본적인 리스트뷰 아이템 리소스
         *
         * simple_list_item_1
         * simple_list_item_2
         * simple_list_item_activated_1
         * simple_list_item_activated_2
         * simple_list_item_checked
         * simple_list_item_multiple_choice
         * simple_list_item_single_choice
         * simple_selectable_list_item
         */

        // 리스트뷰에 ArrayAdapter 객체를 설정하여 리스트뷰에 데이터와 출력 형태를 지정한다.
        listview.setAdapter(adapter);

        // 리스트뷰 선택 시 이벤트를 설정한다.
        listview.setOnItemClickListener(this);

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setListView() {
        adapter = new ArrayAdapter<String>(
                this, R.layout.simplestore_item, storename_list) {
            //override getView to change the item text color
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.BLACK);
                return view;
            }
        };
    }




    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        // arg1는 현재 리스트에 뿌려지고 있는 정보
        // arg2는 현재 리스트에 뿌려지고 있는 해당 id 값


        // 값 출력을 위해 불러온 도구를 id값을 통해 불러옴
        //TextView a = (TextView) findViewById(R.id.textView1);

        // 현재 리스트뷰에 있는 해당 값을 보기
        TextView tv = (TextView) arg1;

        // 현재 리스트뷰에 나오는 문자열과 해당 라인의 id값을 확인
        //a.setText("선택된 값 : " + tv.getText() + "\n선택된 id값: " + arg2);
        storename_selected = tv.getText().toString();

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_for_result = new Intent();
                intent_for_result.putExtra("storename_selected", storename_selected);
                Log.d("storename_selected", storename_selected);
                //Toast.makeText(StoreListActivity.this, storename_selected,Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, intent_for_result);

                //startActivity(intent);
                //onBackPressed();
                finish();
                overridePendingTransition(R.anim.fadeout, R.anim.fadeout);

                //Intent intent_for_recommend_list = new Intent();
            }
        });


    }
}