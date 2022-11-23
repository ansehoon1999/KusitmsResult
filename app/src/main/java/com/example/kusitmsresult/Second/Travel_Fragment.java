package com.example.kusitmsresult.Second;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.kusitmsresult.R;
import com.example.kusitmsresult.Travel_Fragment.CalendarActivity;
import com.example.kusitmsresult.Travel_Fragment.StartTravelActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Travel_Fragment extends Fragment implements View.OnClickListener {

    public Travel_Fragment() {

    }
    public static Travel_Fragment newInstance() {
        return new Travel_Fragment ();
    }



    private Button travel_writeButton;
    private Button travel_startButton;
    private DatabaseReference reference;
    private String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public ArrayList<String> placeList;
    public ArrayList<String> userUidList;

    private ImageView imageView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_travel, container, false);

        travel_writeButton = rootview.findViewById(R.id.travel_writeButton);
        travel_startButton = rootview.findViewById(R.id.startTravel_endButton);

        travel_writeButton.setOnClickListener(this);
        travel_startButton.setOnClickListener(this);

        imageView = rootview.findViewById(R.id.flyingAirplane);
        Animation animation= AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.plane_anim);
        imageView.startAnimation(animation);


        placeList = new ArrayList();
        userUidList = new ArrayList<>();



        return rootview;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.travel_writeButton :


                Intent intent2 = new Intent(getActivity(), CalendarActivity.class);
                startActivity(intent2);




                break;
            case R.id.startTravel_endButton :

                reference = FirebaseDatabase.getInstance().getReference();
                reference.child("place_rate").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String place = snapshot.child("place").getValue(String.class);
                            String rating = snapshot.child("rating").getValue(String.class);
                            String eachUid = snapshot.child("uid").getValue(String.class);

                            userUidList.add(eachUid);
                            placeList.add(place);
                        }

                        Intent intent2 = new Intent(getActivity(), StartTravelActivity.class);
                        intent2.putExtra("userUidList", userUidList);
                        intent2.putExtra("placeList", placeList);

                        startActivity(intent2);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                break;
        }
    }

}