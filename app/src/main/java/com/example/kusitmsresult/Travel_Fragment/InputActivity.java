package com.example.kusitmsresult.Travel_Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.kusitmsresult.LoadingDialog;
import com.example.kusitmsresult.R;
import com.example.kusitmsresult.model.InputOtherModel;
import com.example.kusitmsresult.model.Personal_Information;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hsalf.smilerating.SmileRating;
import com.hsalf.smileyrating.SmileyRating;
import com.like.LikeButton;
import com.like.OnAnimationEndListener;
import com.like.OnLikeListener;
import com.mikepenz.iconics.IconicsDrawable;

import java.lang.reflect.Array;
import java.util.ArrayList;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.hsalf.smileyrating.SmileyRating.Type.BAD;

public class InputActivity extends AppCompatActivity implements OnLikeListener {


        private EditText Input_placeEditText;
        private EditText Input_dateEditText;
        private EditText Input_timeEditText;
        private EditText Input_commentEditText;
        private Button Input_nextButton;


        private ImageSwitcher ImageSwitcher_ProfileImage;
        private ImageButton ImageSwitcher_previousButton, ImageSwitcher_nextButton;

        private String tmpResult = "";

        private StorageReference reference = FirebaseStorage.getInstance().getReference();

        private String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        private int ii = 0;
        private String place;
        private String date;
        private String time;
        private String secret_code;
        private static final int PICK_IMAGES_CODE = 0;
        private ArrayList<Uri> imageUris;

        private ArrayList<String> FirebaseStorageUris;
        int position = 0;

        private LikeButton firstLikeButton;
        private LikeButton secondLikeButton;
        private LikeButton thirdLikeButton;
        private LikeButton forthLikeButton;
        private SmileyRating smile_rating;

        private String firstButton = "FalseFalse";
        private String secondButton = "FalseFalse";
        private String thirdButton = "FalseFalse";
        private String forthButton = "FalseFalse";

        private String firstButton2 = "";
        private String secondButton2 = "";
        private String thirdButton2 = "";
        private String forthButton2 = "";

        private String ratingValue = "";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_input);

            final LoadingDialog progressDialog = new LoadingDialog(InputActivity.this);

            firstLikeButton = findViewById(R.id.firstLikeButton);
            secondLikeButton = findViewById(R.id.secondLikeButton);
            thirdLikeButton = findViewById(R.id.thirdLikeButton);
            forthLikeButton = findViewById(R.id.forthLikeButton);


            firstLikeButton.setBackground(new ShapeDrawable(new OvalShape()));
            firstLikeButton.setClipToOutline(true);

            secondLikeButton.setBackground(new ShapeDrawable(new OvalShape()));
            secondLikeButton.setClipToOutline(true);

            thirdLikeButton.setBackground(new ShapeDrawable(new OvalShape()));
            thirdLikeButton.setClipToOutline(true);

            forthLikeButton.setBackground(new ShapeDrawable(new OvalShape()));
            forthLikeButton.setClipToOutline(true);

            init();
            Intent intent = getIntent();

            place = intent.getStringExtra("route");
            date = intent.getStringExtra("date");
            time = intent.getStringExtra("time");
            secret_code = intent.getStringExtra("secret_code");

            Input_placeEditText.setText(place);
            Input_timeEditText.setText(time);
            Input_dateEditText.setText(date);

            smile_rating = findViewById(R.id.smile_rating);
            smile_rating.setSmileySelectedListener(new SmileyRating.OnSmileySelectedListener() {
                @Override
                public void onSmileySelected(SmileyRating.Type type) {

                    switch (type) {
                        case BAD:
                            ratingValue = "2점";
                            break;

                        case GOOD:
                            ratingValue = "4점";

                            break;

                        case GREAT:
                            ratingValue = "5점";

                            break;

                        case OKAY:
                            ratingValue = "3점";

                            break;

                        case TERRIBLE:
                            ratingValue = "1점";

                            break;
                    }
                }
            });


            firstLikeButton.setOnAnimationEndListener(new OnAnimationEndListener() {
                @Override
                public void onAnimationEnd(LikeButton likeButton) {
                }
            });

            firstLikeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {

                    firstButton = "TrueTrue";
                    firstButton2 = "T";
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    firstButton = "FalseFalse";
                }
            });


            secondLikeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    secondButton = "TrueTrue";
                    secondButton2 = "R";

                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    secondButton = "FalseFalse";
                }
            });

            thirdLikeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    thirdButton = "TrueTrue";
                    thirdButton2 = "I";

                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    thirdButton = "FalseFalse";
                }
            });

            forthLikeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {

                    forthButton = "TrueTrue";
                    forthButton2 = "P";
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    forthButton = "FalseFalse";
                }
            });


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
                    if (position < imageUris.size() - 1) {
                        position++;
                        ImageSwitcher_ProfileImage.setImageURI(imageUris.get(position));


                    } else {
                        Toast.makeText(InputActivity.this, "더 이상의 이미지는 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ImageSwitcher_previousButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position > 0) {
                        position--;
                        ImageSwitcher_ProfileImage.setImageURI(imageUris.get(position));
                    } else {
                        Toast.makeText(InputActivity.this, "이전 이미지는 없습니다.", Toast.LENGTH_SHORT).show();
                    }
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


                        Personal_Information personal_information = new Personal_Information();

                        personal_information.route = Input_placeEditText.getText().toString();
                        personal_information.date = Input_dateEditText.getText().toString();
                        personal_information.time = Input_timeEditText.getText().toString();
                        personal_information.rate = ratingValue;
                        personal_information.comment = Input_commentEditText.getText().toString();
                        personal_information.state = "false";
                        personal_information.LikeButton = firstButton2 + secondButton2 + thirdButton2 + forthButton2;



                        FirebaseDatabase.getInstance().getReference().child("Personal_Information")
                                .child(secret_code).child(secret_code + Input_timeEditText.getText().toString()).setValue(personal_information);

                        //==========================================================================================

                        InputOtherModel inputOtherModel = new InputOtherModel();
                        inputOtherModel.secretCode = secret_code;
                        inputOtherModel.place = Input_placeEditText.getText().toString();
                        inputOtherModel.rating = ratingValue;
                        inputOtherModel.time = Input_timeEditText.getText().toString();
                        inputOtherModel.comment = Input_commentEditText.getText().toString();
                        inputOtherModel.state = "false";
                        inputOtherModel.uid = myUid;

                        FirebaseDatabase.getInstance().getReference().child("Other_information")
                                .child(secret_code).child(secret_code + Input_timeEditText.getText().toString()).setValue(inputOtherModel);


                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("result", Input_timeEditText.getText().toString());
                        resultIntent.putExtra("place", Input_placeEditText.getText().toString());
                        resultIntent.putExtra("time", Input_timeEditText.getText().toString());
                        resultIntent.putExtra("rating", ratingValue);
                        resultIntent.putExtra("comment", Input_commentEditText.getText().toString());
                        resultIntent.putExtra("uriArray", imageUris);
                        resultIntent.putExtra("firstButton", firstButton);
                        resultIntent.putExtra("secondButton", secondButton);
                        resultIntent.putExtra("thirdButton", thirdButton);
                        resultIntent.putExtra("forthButton", forthButton);


                        for (int i = 0; i < FirebaseStorageUris.size(); i++) {
                            if (i == 0) {
                                tmpResult = FirebaseStorageUris.get(i);
                            } else {
                                tmpResult = tmpResult + "," + FirebaseStorageUris.get(i);
                            }
                        }


                        resultIntent.putExtra("FirebaseStorageUrisString", tmpResult);
                        setResult(RESULT_OK, resultIntent);

                        progressDialog.startLoadingDialog();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {


                                progressDialog.dismissDialog();

                                finish();

                            }
                        }, 1000);


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
            ImageSwitcher_ProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    ImageSwitcher_ProfileImage.setBackground(null);
                    pickImagesIntent();
                }
            });

            ImageSwitcher_nextButton = findViewById(R.id.ImageSwitcher_nextButton);
            ImageSwitcher_previousButton = findViewById(R.id.ImageSwitcher_previousButton);

            imageUris = new ArrayList<>();
            FirebaseStorageUris = new ArrayList<>();


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

            if (requestCode == PICK_IMAGES_CODE) {
                if (resultCode == Activity.RESULT_OK) {
                    if (data.getClipData() != null) {
                        int cout = data.getClipData().getItemCount();
                        for (int i = 0; i < cout; i++) {
                            Uri imageUri = data.getClipData().getItemAt(i).getUri();
                            imageUris.add(imageUri);

                        }

                        ImageSwitcher_ProfileImage.setImageURI(imageUris.get(0));
                        position = 0;
                    } else {
                        Uri imageUri = data.getData();
                        imageUris.add(imageUri);
                        ImageSwitcher_ProfileImage.setImageURI(imageUris.get(0));
                        position = 0;


                    }

                    FirebaseStorageUris.clear();


                    for (ii = 0; ii < imageUris.size(); ii++) {

                        FirebaseStorage.getInstance().getReference().child("Personal_Information")
                                .child(secret_code)
                                .child(secret_code + Input_placeEditText.getText().toString())
                                .child(secret_code + Input_placeEditText.getText().toString() + String.valueOf(ii) + ".png")
                                .putFile(imageUris.get(ii));

                        final StorageReference fileRef = reference.child("Above_Information").child(secret_code)
                                .child(secret_code + Input_placeEditText.getText().toString() + String.valueOf(ii) + ".png");

                        fileRef.putFile(imageUris.get(ii)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        FirebaseStorageUris.add(uri.toString());


                                    }
                                });
                            }
                        });

                    }


                }


            }


        }


        @Override
        public void liked(LikeButton likeButton) {

        }

        @Override
        public void unLiked(LikeButton likeButton) {

        }
    }