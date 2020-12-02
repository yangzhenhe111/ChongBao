package com.example.pet.my;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pet.R;
import com.example.pet.my.order.MyOrderActivity;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.Article;
import com.example.pet.other.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyFragment extends Fragment {

    private  View view;
private  TextView infoName;
private  TextView infoAutograph;
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
        setViewContent();
        return view;
    }
//设置控件内容
    private void setViewContent() {
        Cache.user = new User();
        Cache.user.setPicturePath("/imgs/1.png");
        Cache.user.setUserName("洛洛");
        Cache.user.setUserAutograph("我心向阳");
        try {
            InputStream photoStream = new URL(Cache.MY_URL+Cache.user.getPicturePath()).openStream();
            infoPhoto.setImageBitmap(BitmapFactory.decodeStream(photoStream));
        } catch (IOException e) {
            e.printStackTrace();
        }

        infoName.setText(Cache.user.getUserName());
        infoAutograph.setText(Cache.user.getUserAutograph());

    }

    //获得控件和监听器
    private void setView() {
        MyListener listener = new MyListener();
        infoArticle = view.findViewById(R.id.info_article);
        infoArticle.setOnClickListener(listener);
        infoOrder = view.findViewById(R.id.info_order);
        infoOrder.setOnClickListener(listener);
        infoUpdate = view.findViewById(R.id.info_update);
        infoUpdate.setOnClickListener(listener);
        infoHistory = view.findViewById(R.id.info_history);
        infoHistory.setOnClickListener(listener);
        infoPet = view.findViewById(R.id.info_pet);
        infoPet.setOnClickListener(listener);
        infoSetting = view.findViewById(R.id.info_set);
        infoSetting.setOnClickListener(listener);
        infoAboutUs = view.findViewById(R.id.info_about_us);
        infoAboutUs.setOnClickListener(listener);
        infoPhoto = view.findViewById(R.id.info_photo);
        infoPhoto.setOnClickListener(listener);
        infoName = view.findViewById(R.id.info_name);
        infoAutograph = view.findViewById(R.id.info_autograph);
    }
    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.info_article:
                    Intent intent = new Intent(getContext(), Post.class);
                    startActivity(intent);
                    break;
                case R.id.info_order:
                    Intent intent1 = new Intent(getContext(), Order2.class);
                    startActivity(intent1);
                    break;
                case R.id.info_update:
                    Intent intent2 = new Intent(getContext(), EditInfo.class);
                    startActivity(intent2);
                    break;
                case R.id.info_history:
                    Intent intent3 = new Intent(getContext(), History.class);
                    startActivity(intent3);
                    break;
                case R.id.info_pet:
                    Intent intent4 = new Intent(getContext(), Pet.class);
                    startActivity(intent4);
                    break;
                case R.id.info_set:
                    Intent intent5 = new Intent(getContext(), Setting.class);
                    startActivity(intent5);
                    break;
                case R.id.info_about_us:
                    Intent intent6 = new Intent(getContext(), AboutUs.class);
                    startActivity(intent6);
                    break;
                case R.id.info_photo:
                    Intent intent7 = new Intent(getContext(),Login.class);
                    startActivity(intent7);
                    break;

            }
        }
    }

}
