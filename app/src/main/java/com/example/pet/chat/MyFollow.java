package com.example.pet.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.pet.R;

import java.net.MalformedURLException;
import java.net.URL;


public class MyFollow extends Fragment {

    private String title;

    public MyFollow(String title){
        this.title = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myfollow, container, false);
        ListView lv_myfollow = view.findViewById(R.id.lv_myfollow);

        return view;
    }

    public void getAllMyFollow(){
        new Thread(){
            @Override
            public void run() {
                try {
                    int user_id = 2;
                    URL url = new URL("http://192.168.43.202:8080/ChongBaoService_war_exploded/GetAllMyFollow?user_id="+user_id);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
