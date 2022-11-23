package com.example.kusitmsresult.Travel_Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kusitmsresult.R;
import com.example.kusitmsresult.model.Evaluation2_ListItem;
import com.example.kusitmsresult.model.InputOtherModel;
import com.example.kusitmsresult.model.Personal_Information;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class EvaluationActivity2 extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<String> startTravel_arrayList;
    private ArrayList<String> startTravel_timearrayList;
    private ArrayList<String> startTravel_datearrayList;
    private String FirebaseStorageUrisString;


    private String secret_code;

    private ArrayList<String> startTravel_otherList;

    public static final String ORIENTATION = "orientation";

    public static final int evaluation2_eachButtonNum = 1;
    public static final int evaluation2_tmpStoreButtonNum = 2;
    public static final int evaluation2_addButtonNum = 3;

    private LinearLayout gallery;
    private LayoutInflater inflater;
    private Button evaluation2_registerButton;
    private Button evaluation2_tmpStoreButton;
    private Button evaluation2_addButton;

    private TimelineAdapter adapter;
    private RecyclerView recycler;

    private RecyclerView lastRecyclerView;
    private ArrayList<InputOtherModel> lastArrayList;
    private InputOtherAdapter inputOtherAdapter;

    private ArrayList<Integer> numPhoto;
    private ArrayList<String> FirebaseStorageUris;

    private String result = "";



    private ArrayList<String> likeButton_list;
    private String likeButton_string ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation2);

        init();

        likeButton_list = new ArrayList<>();


        evaluation2_registerButton.setOnClickListener(this);
        evaluation2_tmpStoreButton.setOnClickListener(this);
        evaluation2_addButton.setOnClickListener(this);


        Intent intent = getIntent();
        startTravel_arrayList = (ArrayList<String>) intent.getSerializableExtra("startTravel_arrayList");
        startTravel_timearrayList = (ArrayList<String>) intent.getSerializableExtra("startTravel_timearrayList");
        startTravel_datearrayList = (ArrayList<String>) intent.getSerializableExtra("startTravel_datearrayList");




        secret_code = intent.getStringExtra("secret_code");

        numPhoto = new ArrayList<>();
        for(int i=0;i<startTravel_arrayList.size();i++) {
            numPhoto.add(0);
            FirebaseStorageUris.add("");
        }



        int orientation = getIntent().getIntExtra(ORIENTATION, LinearLayoutManager.VERTICAL);

        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, orientation, false));

        adapter = new TimelineAdapter(orientation, getItems(), new TimelineAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), InputActivity.class);
                intent.putExtra("route", getItems().get(position).getRoute());
                intent.putExtra("date", getItems().get(position).getdate());
                intent.putExtra("time", getItems().get(position).gettime());
                intent.putExtra("secret_code", secret_code);

                startActivityForResult(intent, evaluation2_eachButtonNum);
            }
        });

        recycler.setAdapter(adapter);

        // =======================================================================

        lastRecyclerView = findViewById(R.id.lastRecyclerView);
        lastArrayList = new ArrayList<>();
        initRecyclerView();

        Personal_Information personal_information = new Personal_Information();

        personal_information.route = "";
        personal_information.date = "";
        personal_information.time = "";
        personal_information.rate = "";
        personal_information.comment = "";
        personal_information.state = "false";
        personal_information.image = "";
        personal_information.LikeButton="";

        for(int i=0; i< startTravel_timearrayList.size(); i++) {
            FirebaseDatabase.getInstance().getReference().child("Personal_Information")
                    .child(secret_code).child(secret_code + startTravel_timearrayList.get(i)).setValue(personal_information);
        }



    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManger = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        lastRecyclerView.setLayoutManager(linearLayoutManger);
        inputOtherAdapter = new InputOtherAdapter(lastArrayList, getApplicationContext());
        lastRecyclerView.setAdapter(inputOtherAdapter);
    }

    private void init() {
        evaluation2_registerButton = findViewById(R.id.evaluation2_registerButton);
        evaluation2_tmpStoreButton = findViewById(R.id.evaluation2_tmpStoreButton);
        evaluation2_addButton = findViewById(R.id.evaluation2_addButton);
        FirebaseStorageUris = new ArrayList<>();
    }


    private List<Evaluation2_ListItem> getItems() {
        List<Evaluation2_ListItem> items = new ArrayList<>();

        for (int i = 0; i < startTravel_arrayList.size(); i++) {
            Evaluation2_ListItem item = new Evaluation2_ListItem();
            item.setRoute(startTravel_arrayList.get(i));
            item.setdate(startTravel_datearrayList.get(i));
            item.settime(startTravel_timearrayList.get(i));
            items.add(item);
        }

        return items;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.evaluation2_registerButton :
                registerButtonEvent();

                for(int i= 0;i<FirebaseStorageUris.size();i++) {
                    if(i == 0) {
                        result = FirebaseStorageUris.get(i);
                    } else {
                        result = result + "," + FirebaseStorageUris.get(i);
                    }
                }


                FirebaseDatabase.getInstance().getReference().child("Above_information")
                        .child(secret_code).child("uri").setValue(result);



                break;

            case R.id.evaluation2_tmpStoreButton :
                tmpStoreButtonEvent();
                break;


            case R.id.evaluation2_addButton :

                Intent intent = new Intent(getApplicationContext(), InputOtherActivity.class);

                intent.putExtra("route", "");
                intent.putExtra("date", "");
                intent.putExtra("time", "");
                intent.putExtra("secret_code", secret_code);


                startActivityForResult(intent, evaluation2_addButtonNum);

                break;
        }
    }

    private void tmpStoreButtonEvent() {
        for(int i=0; i< startTravel_timearrayList.size(); i++) {
            FirebaseDatabase.getInstance().getReference().child("Personal_Information")
                    .child(secret_code).child(secret_code + startTravel_timearrayList.get(i)).child("state").setValue("tmp");
        }

        FirebaseDatabase.getInstance().getReference().child("Above_information")
                .child(secret_code).child("state").setValue("tmp");

        finish();
    }

    private void registerButtonEvent() {

        for(int i=0; i< startTravel_timearrayList.size(); i++) {
            FirebaseDatabase.getInstance().getReference().child("Personal_Information")
                    .child(secret_code).child(secret_code + startTravel_timearrayList.get(i)).child("state").setValue("true");


        }


        FirebaseDatabase.getInstance().getReference().child("Above_information")
                .child(secret_code).child("state").setValue("true");

        String result = "";
        for(int i= 0;i<numPhoto.size();i++) {
            if ( i==0) {
                result = result + String.valueOf(numPhoto.get(i));
            }
            else {
                result = result + "@" + String.valueOf(numPhoto.get(i));
            }
        }

        for(int i=0;i<likeButton_list.size();i++) {
            if(i==0) {
                likeButton_string =likeButton_string + likeButton_list.get(i);

            }
            likeButton_string =likeButton_string + "@" + likeButton_list.get(i);
        }


        FirebaseDatabase.getInstance().getReference().child("Above_information")
                .child(secret_code).child("numOfPhoto").setValue(result);

        FirebaseDatabase.getInstance().getReference().child("Above_information")
                .child(secret_code).child("buttonArray").setValue(likeButton_string);


        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case evaluation2_addButtonNum :
                    startTravel_timearrayList.add(data.getStringExtra("result"));


                    String time = data.getStringExtra("time");
                    String rating = data.getStringExtra("rating");
                    String place = data.getStringExtra("place");
                    String comment = data.getStringExtra("comment");
                    ArrayList<Uri> uriArray = (ArrayList<Uri>) data.getSerializableExtra("uriArray");

                    InputOtherModel inputOtherModel = new InputOtherModel();
                    inputOtherModel.time = time;
                    inputOtherModel.rating = rating;
                    inputOtherModel.place =place;
                    inputOtherModel.comment = comment;
                    inputOtherModel.uri = uriArray;

                    startTravel_arrayList.add(place);
                    lastArrayList.add(inputOtherModel);
                    numPhoto.add(uriArray.size());



                    lastRecyclerView.setAdapter(inputOtherAdapter);



                    break;


                case evaluation2_eachButtonNum:

                    String time2 = data.getStringExtra("time");
                    String rating2 = data.getStringExtra("rating");
                    String place2 = data.getStringExtra("place");
                    String comment2 = data.getStringExtra("comment");
                    ArrayList<Uri> uriArray2 = (ArrayList<Uri>) data.getSerializableExtra("uriArray");
                    FirebaseStorageUrisString = data.getStringExtra("FirebaseStorageUrisString");

                    String FirstLikeButton2 = data.getStringExtra("firstButton");
                    String SecondLikeButton2 = data.getStringExtra("secondButton");
                    String ThirdLikeButton2 = data.getStringExtra("thirdButton");
                    String ForthLikeButton2 = data.getStringExtra("forthButton");


                    if (FirstLikeButton2.equals(String.valueOf("TrueTrue"))) {
                            likeButton_list.add("T");
                    }

                    if (SecondLikeButton2.equals(String.valueOf("TrueTrue"))) {
                        likeButton_list.add("R");
                    }

                    if (ThirdLikeButton2.equals(String.valueOf("TrueTrue"))) {
                        likeButton_list.add("I");

                    }
                    if (ForthLikeButton2.equals(String.valueOf("TrueTrue"))) {
                        likeButton_list.add("P");
                    }




                    for (int i=0;i<numPhoto.size();i++) {
                        if(startTravel_arrayList.get(i).equals(place2)) {
                            numPhoto.set(i, uriArray2.size());
                            FirebaseStorageUris.set(i, FirebaseStorageUrisString);

//                            Toast.makeText(this, FirebaseStorageUris.get(i), Toast.LENGTH_SHORT).show();
                        }
                    }
            }


        }
    }
}
