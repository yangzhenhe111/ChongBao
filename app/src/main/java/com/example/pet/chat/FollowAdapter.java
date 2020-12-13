package com.example.pet.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class FollowAdapter extends FragmentPagerAdapter {
    private List<Fragment> lists;
    private List<String> titles;

    public FollowAdapter(@NonNull FragmentManager fm, List<Fragment> lists, List<String> titles) {
        super(fm);
        this.lists = lists;
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return lists.get(position);
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //把标题跟fragment的数据绑定起来
        return titles.get(position);
    }
}
