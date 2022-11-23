package com.example.kusitmsresult.Travel_Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.kusitmsresult.R;
import com.example.kusitmsresult.model.AboveModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Gone1Activity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;
    private int ACCESS_LOCATION_REQUEST_CODE = 1001;
    private Button gone1_nextButton;
    private Button gone1_storeButton;

    private ArrayList<String> arrayList;
    private String resultArray = "";
    private String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private String Calendar_selectedDate;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gone1);

        Intent intent = getIntent();
        Calendar_selectedDate = intent.getStringExtra("Calendar_selectedDate");
        date = intent.getStringExtra("date");

        searchView = findViewById(R.id.sv_location);
        gone1_nextButton = findViewById(R.id.gone1_nextButton);
        gone1_storeButton = findViewById(R.id.gone1_storeButton);

        gone1_nextButton.setOnClickListener(this);
        gone1_storeButton.setOnClickListener(this);
        arrayList = new ArrayList<String>();


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.gone1_google_map);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if(location != null || !location.equals(""))  {
                    Geocoder geocoder = new Geocoder(Gone1Activity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);


                    } catch (IOException e) {

                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));



                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.gone1_storeButton :
                arrayList.add(searchView.getQuery().toString());
                Toast.makeText(this, searchView.getQuery().toString() + " 가 추가되었습니다.", Toast.LENGTH_SHORT).show();

                break;



            case R.id.gone1_nextButton :
                if(arrayList.size() == 0) {
                    Toast.makeText(this, "위치를 검색한 후에 저장해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), EvaluationActivity1.class);
                    intent.putExtra("arrayList", arrayList);
                    intent.putExtra("date", date);
                    intent.putExtra("Calendar_selectedDate", Calendar_selectedDate);
                    startActivity(intent);
                }
                break;
        }
    }
}