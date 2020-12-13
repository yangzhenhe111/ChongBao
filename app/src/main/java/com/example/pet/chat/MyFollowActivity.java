package com.example.pet.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.pet.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MyFollowActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> list = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_follow);
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