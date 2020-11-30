package com.example.pet.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baidu.mapapi.map.Circle;
import com.example.pet.R;
import com.example.pet.other.entity.Article;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyFragment extends Fragment {

    private  View view;

    private TextView infoArticle;
    private TextView infoOrder;
    private TextView infoUpdate;
    private TextView infoHistory;
    private TextView infoPet;
    private TextView infoSetting;
    private TextView infoAboutUs;
    private CircleImageView infoPhoto;

    public MyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my,container,false);
        //初始化
        setView();

        return view;
    }

    private void setView() {
        infoArticle = view.findViewById(R.id.info_article);
        infoArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(getContext(), Post.class);
                startActivity(intent);
            }
        });
        infoOrder = view.findViewById(R.id.info_order);
        infoOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(getContext(),Order2.class);
                startActivity(intent);
            }
        });
        infoUpdate = view.findViewById(R.id.info_update);
        infoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(getContext(),EditInfo.class);
                startActivity(intent);
            }
        });
        infoHistory = view.findViewById(R.id.info_history);
        infoHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(getContext(),History.class);
                startActivity(intent);
            }
        });
        infoPet = view.findViewById(R.id.info_pet);
        infoPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(getContext(),Pet.class);
                startActivity(intent);
            }
        });
        infoSetting = view.findViewById(R.id.info_set);
        infoSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(getContext(),Setting.class);
                startActivity(intent);
            }
        });
        infoAboutUs = view.findViewById(R.id.info_about_us);
        infoAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(getContext(),AboutUs.class);
                startActivity(intent);
            }
        });
        infoPhoto = view.findViewById(R.id.info_photo);
        infoPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(getContext(),Login.class);
                startActivity(intent);
            }
        });
    }

}
