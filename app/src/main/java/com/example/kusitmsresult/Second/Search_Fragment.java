package com.example.kusitmsresult.Second;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kusitmsresult.Search_Fragment.searchPage_SearchAdapter;
import com.example.kusitmsresult.R;
import com.example.kusitmsresult.Search_Fragment.searchPage_SearchDetailActivity;
import com.example.kusitmsresult.model.AboveModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Search_Fragment extends Fragment {

    public Search_Fragment() {

    }
    public static Search_Fragment newInstance() {
        return new Search_Fragment ();
    }



    EditText txtSearch;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManger;
    private ArrayList<AboveModel> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private Context context;
    private String DestinationUid;

    private ArrayList<String> placeList;
    private ArrayList<String> ratingList;
    private ArrayList<String> commentList;

    private ArrayList<String> likeButtonList;

    private String buttonArray;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootview = inflater.inflate(R.layout.fragment_search, container, false);


        likeButtonList = new ArrayList<>();
       placeList = new ArrayList<>();
       ratingList = new ArrayList<>();
       commentList = new ArrayList<>();

        context = rootview.getContext();

        txtSearch = rootview.findViewById(R.id.txtSearch);
        recyclerView = rootview.findViewById(R.id.listData2);

        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존성능 강화
        layoutManger = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManger);

        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance(); //파이어베이스 데이터베이스 연동

        databaseReference = database.getReference("Above_information"); //파이어 베이스에서 user



        txtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                switch(i) {
                    case KeyEvent.KEYCODE_ENTER :
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(txtSearch.getWindowToken(), 0);    //hide keyboard

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                                arrayList.clear(); //기존 배열리스트가 존재하지 않게 초기화
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //반복문으로 데이터 list를 추출해냄
                                    AboveModel aboveModel = snapshot.getValue(AboveModel.class); //만들어뒀던 User 객체에 데이터를 담는다

                                    buttonArray = aboveModel.buttonArray;
                                    if(aboveModel.path.contains(txtSearch.getText().toString())){
                                        arrayList.add(aboveModel); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비

                                    } else {

                                    }

                                }

                                new Handler().postDelayed(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {

                                    }
                                }, 4000);// 0.5초 정도 딜레이를 준 후 시작

                                ((searchPage_SearchAdapter) adapter).isShimmer= false;

                                adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        adapter = new searchPage_SearchAdapter(arrayList, context, new searchPage_SearchAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Intent intent = new Intent(context, searchPage_SearchDetailActivity.class);

                                String path = arrayList.get(position).path;
                                String date = arrayList.get(position).date;
                                String destinationUid = arrayList.get(position).secretCode;
                                String timeLine = arrayList.get(position).time;
                                String[] timeLineLength = timeLine.split("@");

                                String uriLine = arrayList.get(position).uri;
                                String numOfPhoto = arrayList.get(position).numOfPhoto;



                                DestinationUid = destinationUid;


                                databaseReference = database.getReference("Personal_Information").child(DestinationUid); //파이어 베이스에서 user
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            String route = snapshot.child("route").getValue(String.class); //uid
                                            String rate = snapshot.child("rate").getValue(String.class); //uid
                                            String comment = snapshot.child("comment").getValue(String.class); //uid
                                            String button_list = snapshot.child("LikeButton").getValue(String.class);



                                            placeList.add(route);
                                            ratingList.add(rate);
                                            commentList.add(comment);
                                            likeButtonList.add(button_list);
                                        }



                                        intent.putExtra("SecretKey", DestinationUid);
                                        intent.putExtra("TimeLine", timeLine);
                                        intent.putExtra("number", timeLineLength.length);
                                        intent.putExtra("path", path);
                                        intent.putExtra("date", date);

                                        intent.putExtra("placeList", placeList);
                                        intent.putExtra("ratingList", ratingList);
                                        intent.putExtra("commentList", commentList);

                                        intent.putExtra("uriLine", uriLine);
                                        intent.putExtra("numOfPhoto", numOfPhoto);
                                        intent.putExtra("buttonArray", likeButtonList);
                                        intent.putExtra("buttonArray2", buttonArray);

                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        });

                        recyclerView.setAdapter(adapter); //리사이클러뷰에 어댑터 연결




                        break;
                }


                return true;
            }
        });




        return rootview;
    }
}
