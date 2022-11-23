package com.example.kusitmsresult.Second;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kusitmsresult.First.LoginActivity;
import com.example.kusitmsresult.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;


public class MyPage_Fragment extends Fragment {
    private FirebaseAuth auth;
    private Button MyPage_LogOutButton;

    public MyPage_Fragment() {

    }
    public static MyPage_Fragment newInstance() {
        return new MyPage_Fragment ();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_my_page, container, false);


        auth = FirebaseAuth.getInstance();

        MyPage_LogOutButton = rootview.findViewById(R.id.MyPage_LogOutButton);
        MyPage_LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                LoginManager.getInstance().logOut();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });


        return rootview;
    }
}