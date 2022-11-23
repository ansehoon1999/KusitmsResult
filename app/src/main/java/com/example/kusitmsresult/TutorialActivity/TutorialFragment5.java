package com.example.kusitmsresult.TutorialActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kusitmsresult.First.LoginActivity;
import com.example.kusitmsresult.R;
import com.example.kusitmsresult.Second.MainActivity;

public class TutorialFragment5 extends Fragment {

    private Button Tutorial_tmp_Button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview =  inflater.inflate(R.layout.fragment_tutorial5, container, false);

        Tutorial_tmp_Button = rootview.findViewById(R.id.Tutorial_tmp_Button);
        Tutorial_tmp_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });



        return rootview;
    }
}