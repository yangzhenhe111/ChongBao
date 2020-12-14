package com.example.pet.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.pet.R;
import com.example.pet.forum.MyAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class AddressManage extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> list = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manage);
        setView();
    }

    private void setView() {
        Toolbar toolbar = findViewById(R.id.address_manage_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressManage.this.finish();
            }
        });
        tabLayout =  findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_page);
        titles.clear();
        list.clear();
        titles.add("起点");
        titles.add("终点");
        list.add(new AddressFragment());
        list.add(new AddressFragment());
        com.example.pet.forum.MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager(),list,titles);
        //设置适配器
        viewPager.setAdapter(myAdapter);
        //绑定显示效果
        tabLayout.setupWithViewPager(viewPager);
    }
}