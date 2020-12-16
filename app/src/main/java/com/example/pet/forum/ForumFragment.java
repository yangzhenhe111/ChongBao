package com.example.pet.forum;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.pet.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class ForumFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> list = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private View view;
    private Toolbar toolbar;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view = inflater.inflate(R.layout.fragment_forum, container, false);
            tabLayout=view.findViewById(R.id.tablayout);
            viewPager=view.findViewById(R.id.viewpage);
            toolbar=view.findViewById(R.id.toolbar);
            inittoolbar();
            init_tablayout();
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    public void inittoolbar(){
        toolbar.inflateMenu(R.menu.menu_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.search_bar:
                        Intent intent = new Intent(getContext(),SearchResult.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }

    public void init_tablayout(){
        titles.clear();
        list.clear();
        titles.add("文章");
        titles.add("食品");
        titles.add("用品");
        titles.add("日常");
        titles.add("美容");
        titles.add("训练");
        titles.add("驱虫");
        titles.add("病症");
        titles.add("杂谈");
        titles.add("提问");
        titles.add("晒图");
        list.add(new InnerFragment_first("文章"));
        list.add(new InnerFragment_other("食品"));
        list.add(new InnerFragment_other("用品"));
        list.add(new InnerFragment_other("日常"));
        list.add(new InnerFragment_other("美容"));
        list.add(new InnerFragment_other("训练"));
        list.add(new InnerFragment_other("驱虫"));
        list.add(new InnerFragment_other("病症"));
        list.add(new InnerFragment_other("杂谈"));
        list.add(new InnerFragment_other("提问"));
        list.add(new InnerFragment_other("晒图"));

        MyAdapter myAdapter = new MyAdapter(getFragmentManager(),list,titles);
        //设置适配器
        viewPager.setAdapter(myAdapter);
        //绑定显示效果
        tabLayout.setupWithViewPager(viewPager);
    }

}
