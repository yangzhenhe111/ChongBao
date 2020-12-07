package com.example.pet.my;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.pet.R;
import com.example.pet.other.Cache;

import java.util.ArrayList;
import java.util.List;

public class NewPet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pet);
        setView();

    }

    private void setView() {
        ViewPager pager = findViewById(R.id.pet_pager);
        Toolbar toolbar = findViewById(R.id.pet_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewPet.this.finish();
            }
        });
        List<PageFragment> list = new ArrayList<>();
        for(int i=0;i< Cache.myPetList.size();i++){
            PageFragment fragment = new PageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index",i);
            fragment.setArguments(bundle);
            list.add(fragment);
        }
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
    }
}