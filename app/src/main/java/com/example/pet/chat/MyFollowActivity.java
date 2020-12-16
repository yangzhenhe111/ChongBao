package com.example.pet.chat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.pet.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MyFollowActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> list = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_follow);
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;  //因为背景为浅色，设置通知栏字体颜色为深色
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);//设置为透明
        init_tablayout();
    }

    public void init_tablayout(){
        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewpage);
        titles.clear();
        list.clear();
        titles.add("关注");
        titles.add("粉丝");
        list.add(new FollowMe("关注"));
        list.add(new MyFollow("粉丝"));


        //放在fragment里用getFragmentManager()
        FollowAdapter myAdapter = new FollowAdapter(getSupportFragmentManager(),list,titles);
        //设置适配器
        viewPager.setAdapter(myAdapter);
        //绑定显示效果
        tabLayout.setupWithViewPager(viewPager);
    }
}