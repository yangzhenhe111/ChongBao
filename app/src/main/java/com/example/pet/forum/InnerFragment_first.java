package com.example.pet.forum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pet.R;
import com.example.pet.other.entity.Tips;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

public class InnerFragment_first extends Fragment {

    private List<Tips> tipsList;
    private RecyclerView recyclerView;
    private RecyAdapter recyAdapter;
    private Banner banner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inner_first, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);
        banner = view.findViewById(R.id.banner);
        init();
        initBanner();
        return view;
    }

    public void init(){
        tipsList = new ArrayList<>();
        for (int i=0;i<10;i++){
            Tips tips = new Tips();
            tips.setComments(i);
            tips.setLikes(i);
            tips.setTime("时间"+i);
            tips.setTitle("标题"+i);
            tipsList.add(tips);
        }
        recyAdapter = new RecyAdapter(getContext(),tipsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyAdapter);
    }
    public void initBanner(){
        ArrayList<Integer> images = new ArrayList<>();
        ArrayList<String> title = new ArrayList<>();
        title.add("猫咪的洗澡和驱虫");
        title.add("猫咪的日常用品");
        title.add("猫咪的食物");
        title.add("猫咪的日常护理");
        title.add("猫咪的常见病症");
        images.add(R.drawable.recommend1);
        images.add(R.drawable.recommend2);
        images.add(R.drawable.recommend3);
        images.add(R.drawable.recommend4);
        images.add(R.drawable.recommend5);

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setBannerTitles(title);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.start();
    }
}