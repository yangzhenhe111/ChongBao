package com.example.pet.forum;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pet.R;
import com.example.pet.other.entity.Tips;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

public class ForumFragment extends Fragment {

    private EditText et_search;
    private Button btn_search;
    private Button btn_publish;
    private RelativeLayout forum_food;
    private RelativeLayout forum_articles;
    private RelativeLayout forum_daily;
    private RelativeLayout forum_cosmetology;
    private RelativeLayout forum_train;
    private RelativeLayout forum_deworming;
    private RelativeLayout forum_disease;
    private RelativeLayout forum_randomtalk;
    private RelativeLayout forum_question;
    private RelativeLayout forum_sharephotos;
    private TextView tv_newest;
    private TextView tv_hot;
    private TextView tv_essence;
    private ListView lv_tips;
    private Banner banner;
    private ArrayList<Tips> arrayList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum,container,false);
        et_search = view.findViewById(R.id.et_main_search);
        btn_search = view.findViewById(R.id.btn_main_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_publish = view.findViewById(R.id.btn_publish_articles);
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),PublishActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        forum_food = view.findViewById(R.id.forum_food);
        forum_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                getContext().startActivity(intent);
            }
        });
        forum_articles = view.findViewById(R.id.forum_articles);
        forum_articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                getContext().startActivity(intent);
            }
        });
        forum_daily = view.findViewById(R.id.forum_daily);
        forum_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                getContext().startActivity(intent);
            }
        });
        forum_cosmetology = view.findViewById(R.id.forum_cosmetology);
        forum_cosmetology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                getContext().startActivity(intent);
            }
        });
        forum_train = view.findViewById(R.id.forum_train);
        forum_train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                getContext().startActivity(intent);
            }
        });
        forum_deworming = view.findViewById(R.id.forum_deworming);
        forum_deworming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                getContext().startActivity(intent);
            }
        });
        forum_disease = view.findViewById(R.id.forum_disease);
        forum_disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                getContext().startActivity(intent);
            }
        });
        forum_randomtalk = view.findViewById(R.id.forum_randomtalk);
        forum_randomtalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                getContext().startActivity(intent);
            }
        });
        forum_question = view.findViewById(R.id.forum_question);
        forum_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                getContext().startActivity(intent);
            }
        });
        forum_sharephotos = view.findViewById(R.id.forum_sharephotos);
        forum_sharephotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ClassifiedForumActivity.class);
                getContext().startActivity(intent);
            }
        });
        tv_newest = view.findViewById(R.id.forum_newest);
        TextPaint base = tv_newest.getPaint();
        base.setFakeBoldText(true);
        tv_hot = view.findViewById(R.id.forum_hot);
        tv_essence = view.findViewById(R.id.forum_essence);
        tv_newest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_newest.setBackgroundColor(getResources().getColor(R.color.Blue));
                tv_hot.setBackgroundColor(getResources().getColor(R.color.White));
                tv_essence.setBackgroundColor(getResources().getColor(R.color.White));
                TextPaint newest = tv_newest.getPaint();
                newest.setFakeBoldText(true);
                TextPaint hot = tv_hot.getPaint();
                hot.setFakeBoldText(false);
                TextPaint essence = tv_essence.getPaint();
                essence.setFakeBoldText(false);
            }
        });
        tv_hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_newest.setBackgroundColor(getResources().getColor(R.color.White));
                tv_hot.setBackgroundColor(getResources().getColor(R.color.Orange));
                tv_essence.setBackgroundColor(getResources().getColor(R.color.White));
                TextPaint newest = tv_newest.getPaint();
                newest.setFakeBoldText(false);
                TextPaint hot = tv_hot.getPaint();
                hot.setFakeBoldText(true);
                TextPaint essence = tv_essence.getPaint();
                essence.setFakeBoldText(false);
            }
        });
        tv_essence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_newest.setBackgroundColor(getResources().getColor(R.color.White));
                tv_hot.setBackgroundColor(getResources().getColor(R.color.White));
                tv_essence.setBackgroundColor(getResources().getColor(R.color.Purple));
                TextPaint newest = tv_newest.getPaint();
                newest.setFakeBoldText(false);
                TextPaint hot = tv_hot.getPaint();
                hot.setFakeBoldText(false);
                TextPaint essence = tv_essence.getPaint();
                essence.setFakeBoldText(true);
            }
        });


        ArrayList<Integer> images = new ArrayList<>();
        ArrayList<String> title = new ArrayList<>();
        title.add("律政先锋");
        title.add("小妹妹");
        title.add("我曾经的头像");
        images.add(R.drawable.aaaaaa);
        images.add(R.drawable.aaaaaa);
        images.add(R.drawable.aaaaaa);

        banner = view.findViewById(R.id.banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setBannerTitles(title);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.start();

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent;
                switch (position){
                    case 0:
                        intent = new Intent(getContext(),TipsActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getContext(),TipsActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getContext(),TipsActivity.class);
                        startActivity(intent);
                        break;
                }
                Log.e("点击了第几张轮播图:",position+"");
            }
        });

        lv_tips = view.findViewById(R.id.lv_tips);
        for (int i = 0; i < 5; i++) {
            Tips tips = new Tips();
            tips.setId(1);
            tips.setName("名字" + i);
            tips.setTime("2020-11-28/16:36:0" + i);
            tips.setTopic("标签" + i);
            tips.setTitle("标题" + i);
            tips.setText("正文" + i);
            arrayList.add(tips);
        }
        MainForumTipsAdapter mainForumTipsAdapter = new MainForumTipsAdapter(getContext(),arrayList,R.layout.forum_tips_item);
        lv_tips.setAdapter(mainForumTipsAdapter);
        return view;
    }

    public void init(){

    }

    public void search(){
        new Thread(){
            @Override
            public void run() {
                String search = et_search.getText().toString();
            }
        };
    }
}
