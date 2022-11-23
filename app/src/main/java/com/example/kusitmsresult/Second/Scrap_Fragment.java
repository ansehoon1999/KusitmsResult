package com.example.kusitmsresult.Second;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kusitmsresult.R;
import com.example.kusitmsresult.Scrap_Fragment.ScrapAdapter;
import com.example.kusitmsresult.Scrap_Fragment.scrapPage_ScrapDetailActivity;
import com.example.kusitmsresult.Search_Fragment.searchPage_SearchAdapter;
import com.example.kusitmsresult.model.AboveModel;
import com.example.kusitmsresult.model.ScrapClickModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Scrap_Fragment extends Fragment {

    public Scrap_Fragment() {

    }
    public static Scrap_Fragment newInstance() {
        return new Scrap_Fragment ();
    }

    private RecyclerView scrap_recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManger;
    private ArrayList<ScrapClickModel> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private Context context;
    private String DestinationUid;

    private ArrayList<String> placeList;
    private ArrayList<String> ratingList;
    private ArrayList<String> commentList;

    private ArrayList<String> likeButtonList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_scrap, container, false);

        likeButtonList = new ArrayList<>();
        placeList = new ArrayList<>();
        ratingList = new ArrayList<>();
        commentList = new ArrayList<>();

        context = rootview.getContext();
        scrap_recyclerView = rootview.findViewById(R.id.scrap_recyclerView);
        scrap_recyclerView.setHasFixedSize(true); //리사이클러뷰 기존성능 강화
        layoutManger = new LinearLayoutManager(context);
        scrap_recyclerView.setLayoutManager(layoutManger);
        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance(); //파이어베이스 데이터베이스 연동

        databaseReference = database.getReference("ScrapClick_information").child(myUid); //파이어 베이스에서 user
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); //기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //반복문으로 데이터 list를 추출해냄
                    ScrapClickModel scrapClickModel = snapshot.getValue(ScrapClickModel.class); //만들어뒀던 User 객체에 데이터를 담는다

                    arrayList.add(scrapClickModel); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }



                adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        adapter = new ScrapAdapter(arrayList, context, new ScrapAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


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

                        Intent intent = new Intent(context, scrapPage_ScrapDetailActivity.class);

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

                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        scrap_recyclerView.setAdapter(adapter); //리사이클러뷰에 어댑터 연결






        return rootview;
        }
        }
