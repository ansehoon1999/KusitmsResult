package com.example.kusitmsresult.Travel_Fragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.kusitmsresult.R;
import com.example.kusitmsresult.model.InputOtherModel;
import com.example.kusitmsresult.model.Personal_Information;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class InputOtherActivity extends AppCompatActivity {
    private EditText Input_placeEditText;
    private EditText Input_dateEditText;
    private EditText Input_timeEditText;
    private EditText Input_commentEditText;
    private Button Input_nextButton;
    private MaterialRatingBar Input_rating;

    private ImageSwitcher ImageSwitcher_ProfileImage;
    private Button ImageSwitcher_goButton;
    private Button ImageSwitcher_previousButton, ImageSwitcher_nextButton;



    private String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();


    private String place;
    private String date;
    private String time;
    private String secret_code;
    private static final int PICK_IMAGES_CODE = 0;
    private ArrayList<Uri> imageUris;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_other);

        init();
        Intent intent = getIntent();

        place = intent.getStringExtra("route");
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");
        secret_code = intent.getStringExtra("secret_code");

        Input_placeEditText.setText(place);
        Input_timeEditText.setText(time);
        Input_dateEditText.setText(date);

        ImageSwitcher_ProfileImage.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());


                return imageView;
            }
        });


        ImageSwitcher_nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position < imageUris.size()-1) {
                    position ++ ;
                    ImageSwitcher_ProfileImage.setImageURI(imageUris.get(position));

                } else {
                    Toast.makeText(getApplicationContext(), "더 이상의 이미지는 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageSwitcher_previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position > 0 ) {
                    position -- ;
                    ImageSwitcher_ProfileImage.setImageURI(imageUris.get(position));
                } else {
                    Toast.makeText(getApplicationContext(), "이전 이미지는 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ImageSwitcher_goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImagesIntent();
            }
        });

        Input_nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUris.size() == 0 || Input_placeEditText.getText().toString().equals("") || Input_dateEditText.getText().toString().equals("")
                        || Input_timeEditText.getText().toString().equals("")
                        || Input_commentEditText.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "사진 및 정보를 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else {

                    for (int i = 0; i<imageUris.size(); i++) {
                        FirebaseStorage.getInstance().getReference().child("Personal_Information")
                                .child(secret_code)
                                .child(secret_code + Input_placeEditText.getText().toString())
                                .child(secret_code + Input_placeEditText.getText().toString() + String.valueOf(i) + ".png")
                                .putFile(imageUris.get(i));

                        FirebaseStorage.getInstance().getReference().child("Above_Information")
                                .child(secret_code)
                                .child(secret_code + Input_placeEditText.getText().toString() + String.valueOf(i) + ".png")
                                .putFile(imageUris.get(i));
                    }

                    //==========================================================================================

                    Personal_Information personal_information = new Personal_Information();

                    personal_information.route = Input_placeEditText.getText().toString();
                    personal_information.date = Input_dateEditText.getText().toString();
                    personal_information.time = Input_timeEditText.getText().toString();
                    personal_information.rate = String.valueOf(Input_rating.getRating());
                    personal_information.comment = Input_commentEditText.getText().toString();
                    personal_information.state = "false";

                    FirebaseDatabase.getInstance().getReference().child("Personal_Information")
                            .child(secret_code).child(secret_code +  Input_timeEditText.getText().toString()).setValue(personal_information);

                    //==========================================================================================

                    InputOtherModel inputOtherModel = new InputOtherModel();
                    inputOtherModel.secretCode = secret_code;
                    inputOtherModel.place = Input_placeEditText.getText().toString();
                    inputOtherModel.rating = String.valueOf(Input_rating.getRating());
                    inputOtherModel.time = Input_timeEditText.getText().toString();
                    inputOtherModel.comment = Input_commentEditText.getText().toString();
                    inputOtherModel.state ="false";
                    inputOtherModel.uid = myUid;

                    FirebaseDatabase.getInstance().getReference().child("Other_information")
                            .child(secret_code).child(secret_code +  Input_timeEditText.getText().toString()).setValue(inputOtherModel);


                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("result", Input_timeEditText.getText().toString());
                    resultIntent.putExtra("place", Input_placeEditText.getText().toString());
                    resultIntent.putExtra("time", Input_timeEditText.getText().toString());
                    resultIntent.putExtra("rating", String.valueOf(Input_rating.getRating()));
                    resultIntent.putExtra("comment", Input_commentEditText.getText().toString());
                    resultIntent.putExtra("uriArray", imageUris);


                    setResult(RESULT_OK, resultIntent);

                    finish();
                }
            }
        });

    }

    private void init() {
        Input_placeEditText = findViewById(R.id.Input_placeEditText);
        Input_dateEditText = findViewById(R.id.Input_dateEditText);
        Input_timeEditText = findViewById(R.id.Input_timeEditText);
        Input_commentEditText = findViewById(R.id.Input_commentEditText);
        Input_nextButton = findViewById(R.id.Input_nextButton);

        ImageSwitcher_ProfileImage = findViewById(R.id.ImageSwitcher_ProfileImage);
        ImageSwitcher_goButton = findViewById(R.id.ImageSwitcher_goButton);
        Input_rating = findViewById(R.id.Input_rating);

        ImageSwitcher_nextButton = findViewById(R.id.ImageSwitcher_nextButton);
        ImageSwitcher_previousButton = findViewById(R.id.ImageSwitcher_previousButton);

        imageUris = new ArrayList<>();


    }

    private void pickImagesIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image(s)"), PICK_IMAGES_CODE);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGES_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                if(data.getClipData() != null) {
                    int cout = data.getClipData().getItemCount();
                    for (int i=0;i<cout;i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        imageUris.add(imageUri);

                    }

                    ImageSwitcher_ProfileImage.setImageURI(imageUris.get(0));
                    position = 0;
                }
                else {
                    Uri imageUri = data.getData();
                    imageUris.add(imageUri);
                    ImageSwitcher_ProfileImage.setImageURI(imageUris.get(0));
                    position = 0;
                }
            }
        }



    }

}