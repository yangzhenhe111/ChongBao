package com.example.pet.my;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pet.R;
import com.example.pet.other.Cache;

import java.util.ArrayList;
import java.util.List;

public class NewPet extends AppCompatActivity {
private LinearLayout index;

    private ImageView[] indicaterViews;
    private final ViewGroup.LayoutParams params_WRAP_CONTENT = new ViewGroup.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pet);
        setView();

    }

    private void setView() {

        index = findViewById(R.id.pet_indicators);
        TextView addPet= findViewById(R.id.add_new_pet);
        addPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(NewPet.this,Pet.class);
                startActivity(intent);
            }
        });
        ViewPager pager = findViewById(R.id.pet_pager);
        Toolbar toolbar = findViewById(R.id.pet_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewPet.this.finish();
            }
        });
        initIndicaterDots(Cache.myPetList.size());
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
        pager.setOnPageChangeListener(new PageChangeListener());
    }
    private void initIndicaterDots(int size) {
        // 初始化索引指示器的容器Layout
        indicaterViews = new ImageView[size];
        // 循环取得小点图片
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params_WRAP_CONTENT);
            imageView.setImageResource(R.drawable.ic_buy_box_indicator_circle);
            imageView.setEnabled(false);// 都设为灰色
            imageView.setPadding(10, 5, 10, 5);
            indicaterViews[i] = imageView;
            indicaterViews[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
            index.addView(imageView);
        }
        if (indicaterViews.length > 0) indicaterViews[0].setEnabled(true);// 设置为白色，即选中状态
    }
    /**
     * 滑动横幅监听器
     */
    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        // 当当前页面被滑动时调用
        @Override
        public void onPageScrolled(int i, float v, int i2) {}

        // 当新的页面被选中时调用
        @Override
        public void onPageSelected(int i) {
            // 设置底部小点选中状态
            setIndicaterIndex(i);
        }

        // 当滑动状态改变时调用
        @Override
        public void onPageScrollStateChanged(int i) {}
    }
    /**
     * 这只当前引导小点的选中
     */
    private void setIndicaterIndex(int position) {
        if (position < 0 || indicaterViews == null || position > indicaterViews.length - 1) return;
        for (int i = 0; i < indicaterViews.length; i++) {
            if (i == position)
                indicaterViews[i].setEnabled(true);
            else
                indicaterViews[i].setEnabled(false);
        }
        if (position == indicaterViews.length - 1) {
            index.setVisibility(View.VISIBLE);
        } else {
            index.setVisibility(View.VISIBLE);
        }
    }
}