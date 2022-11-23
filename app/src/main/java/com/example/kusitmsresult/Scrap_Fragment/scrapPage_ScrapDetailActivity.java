package com.example.kusitmsresult.Scrap_Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kusitmsresult.R;
import com.example.kusitmsresult.Search_Fragment.CardStackCallback;
import com.example.kusitmsresult.Travel_Fragment.InputActivity;
import com.example.kusitmsresult.Travel_Fragment.InputOtherActivity;
import com.example.kusitmsresult.model.AboveModel;
import com.example.kusitmsresult.model.Evaluation2_ListItem;
import com.example.kusitmsresult.model.Personal_Information;
import com.example.kusitmsresult.model.ScrapClickModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

public class scrapPage_ScrapDetailActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private CardStackLayoutManager manager;
    private scrapPage_CardStackAdapter adapter;

    private DatabaseReference reference;
    private String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String SecretKey;
    private List<Personal_Information> baru;

    private ArrayList<String> placeList;
    private ArrayList<String> ratingList;
    private ArrayList<String> commentList;
    private ArrayList<String> likeButtonList;

    private ArrayList<Integer> numOfPhotoArrInt;

    private String uriLine;
    private String numOfPhoto;


    private String numOfPhotoArr[];

    private ArrayList<String> resultList;
    private String resultUriLine[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_detail);

        placeList = new ArrayList<>();
        ratingList = new ArrayList<>();
        commentList = new ArrayList<>();
        numOfPhotoArrInt = new ArrayList<>();
        resultList = new ArrayList<>();

        Intent intent = getIntent();

        SecretKey = intent.getStringExtra("SecretKey");
        String TimeLine = intent.getStringExtra("TimeLine");
        int number = intent.getIntExtra("number", 0);
        String path = intent.getStringExtra("path");
        String date = intent.getStringExtra("date");

        placeList = (ArrayList<String>) intent.getSerializableExtra("placeList");
        ratingList = (ArrayList<String>) intent.getSerializableExtra("ratingList");
        commentList = (ArrayList<String>) intent.getSerializableExtra("commentList");
        likeButtonList = (ArrayList<String>) intent.getSerializableExtra("buttonArray");

        uriLine = intent.getStringExtra("uriLine");

        resultUriLine = uriLine.split(",");
        resultUriLine[0] = resultUriLine[0].replace(" ", "");

        numOfPhoto = intent.getStringExtra("numOfPhoto");
        numOfPhotoArr = numOfPhoto.split("@"); // 2, 4, 2


        for(int i =0 ; i<numOfPhotoArr.length; i++) {
            numOfPhotoArrInt.add(Integer.parseInt(numOfPhotoArr[i]));
        }

        CardStackView cardStackView = findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(this, new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
//                if (direction == Direction.Right){
//                    Toast.makeText(getApplicationContext(), "Direction Right", Toast.LENGTH_SHORT).show();
//                }
//                if (direction == Direction.Top){
//                    Toast.makeText(getApplicationContext(), "Direction Top", Toast.LENGTH_SHORT).show();
//                }
//                if (direction == Direction.Left){
//                    Toast.makeText(getApplicationContext(), "Direction Left", Toast.LENGTH_SHORT).show();
//                }
//                if (direction == Direction.Bottom){
//                    Toast.makeText(getApplicationContext(), "Direction Bottom", Toast.LENGTH_SHORT).show();
//                }

                // Paginating
                if (manager.getTopPosition() == adapter.getItemCount() - 5){
                    paginate();
                }

            }

            @Override
            public void onCardRewound() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
            }
        });
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        adapter = new scrapPage_CardStackAdapter(addList());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());


    }

    private void paginate() {
        List<Personal_Information> old = adapter.getItems();
        baru = new ArrayList<>(addList());
        CardStackCallback callback = new CardStackCallback(old, baru);
        DiffUtil.DiffResult hasil = DiffUtil.calculateDiff(callback);
        adapter.setItems(baru);
        hasil.dispatchUpdatesTo(adapter);
    }

    private List<Personal_Information> addList() {

        int j = 0;
        int nextNum = 0;
        List<Personal_Information> items = new ArrayList<>();



        for(int i=0; i<numOfPhotoArrInt.size();i++) {

            String placeItem = placeList.get(i);
            String ratingItem = ratingList.get(i);
            String commentItem = commentList.get(i);
            String likeButton = likeButtonList.get(i);

            if(i == 0 ) {

                for (j = 0; j < numOfPhotoArrInt.get(i); ++j) {
                    items.add(new Personal_Information(resultUriLine[j], placeItem, ratingItem, commentItem, likeButton));

                }



            } else  {
                for(j = nextNum ;j< nextNum  +  numOfPhotoArrInt.get(i); ++j) {
                    items.add(new Personal_Information(resultUriLine[j], placeItem, ratingItem, commentItem, likeButton));

                }

            }

            nextNum = j;

        }

        return items;
    }
}