package com.example.pet;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.pet.chat.WeChatFragment;
import com.example.pet.forum.ForumFragment;
import com.example.pet.my.MyDataService;
import com.example.pet.my.MyFragment;
import com.example.pet.nursing.NursingFragment;
import com.example.pet.other.myFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;
//import com.mob.MobSDK;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private com.example.pet.other.myFragmentPagerAdapter myFragmentPagerAdapter;
    private List<Fragment> fragmentList; //保存界面的view
    private LinearLayout one,two,three,four,tab;
    private ImageView imone,imtwo,imthree,imfour;
    private HintPopupWindow hintPopupWindow;
    private TextView  tvone,tvtwo,tvthree,tvfour;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hintPopupWindow = new HintPopupWindow(this);
        //短信授权
        //MobSDK.submitPolicyGrantResult(granted, null);
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;  //因为背景为浅色，设置通知栏字体颜色为深色
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);//设置为透明
        initViews();
        initDatas();
        setViewPagerEvent();
        initEvents();
        ononeSelected();
    }
    /**
     * 初始化控件
     */
    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //底部的布局
        tab = findViewById(R.id.tabs);
        one = (LinearLayout) findViewById(R.id.ll_forum);
        two = (LinearLayout) findViewById(R.id.ll_nursing);
        three = (LinearLayout) findViewById(R.id.ll_chat);
        four = (LinearLayout) findViewById(R.id.ll_my);
        //底部显示图标的ImageView
        imone =(ImageView)findViewById(R.id.iv_forum);
        imtwo =(ImageView)findViewById(R.id.iv_nursing);
        imthree =(ImageView)findViewById(R.id.iv_chat);
        imfour =(ImageView)findViewById(R.id.iv_my);
        //按钮对应的文字
        tvone = (TextView) findViewById(R.id.tv_forum);
        tvtwo = (TextView) findViewById(R.id.tv_nursing);
        tvthree = (TextView) findViewById(R.id.tv_chat);
        tvfour =(TextView) findViewById(R.id.tv_my);
    }
    /**
     * 数据初始化 适配fragment
     */
    private void initDatas() {
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new ForumFragment());
        fragmentList.add(new NursingFragment());
        fragmentList.add(new WeChatFragment());
        fragmentList.add(new MyFragment());
        myFragmentPagerAdapter = new myFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(myFragmentPagerAdapter);
    }

    /**
     * ViewPager切换页面的事件
     */
    private void setViewPagerEvent() {
        //设置ViewPager的page监听切换底栏按钮和文字颜色
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                int currentItem = viewPager.getCurrentItem();//获取Fragment数量
                switch (currentItem) {
                    case 0:
                        ononeSelected(); //改变底栏文字和图标颜色
                        break;
                    case 1:
                        ontwoSelected(); //改变底栏文字和图标颜色
                        break;
                    case 2:
                        onthreeSelected(); //改变底栏文字和图标颜色
                        break;
                    case 3:
                        onfourSelected(); //改变底栏文字和图标颜色
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_forum:
                viewPager.setCurrentItem(0);
                ononeSelected();
                break;
            case  R.id.ll_nursing:
                viewPager.setCurrentItem(1);
                ontwoSelected();
                break;
            case  R.id.ll_chat:
                viewPager.setCurrentItem(2);
                onthreeSelected();
                break;
            case  R.id.ll_my:
                viewPager.setCurrentItem(3);
                onfourSelected();
                break;
            default:
                break;
        }
    }
    /**
     * 初始化底部导航栏事件
     */
    private void initEvents() {
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
    }
    public void ononeSelected(){
        //改变底栏文字和图标颜色
        imone.setImageDrawable(getResources().getDrawable(R.drawable.one));
        tvone.setTextColor(getResources().getColor(R.color.bottom_text_press));
        imtwo.setImageDrawable(getResources().getDrawable(R.drawable.twoo));
        tvtwo.setTextColor(getResources().getColor(R.color.bottom_text_normal));
        imfour.setImageDrawable(getResources().getDrawable(R.drawable.four));
        tvthree.setTextColor(getResources().getColor(R.color.bottom_text_normal));
        imthree.setImageDrawable(getResources().getDrawable(R.drawable.three));
        tvfour.setTextColor(getResources().getColor(R.color.bottom_text_normal));
    }
    public void ontwoSelected(){
        imtwo.setImageDrawable(getResources().getDrawable(R.drawable.two));
        tvtwo.setTextColor(getResources().getColor(R.color.bottom_text_press));
        imone.setImageDrawable(getResources().getDrawable(R.drawable.onee));
        tvone.setTextColor(getResources().getColor(R.color.bottom_text_normal));
        imthree.setImageDrawable(getResources().getDrawable(R.drawable.three));
        tvthree.setTextColor(getResources().getColor(R.color.bottom_text_normal));
        imfour.setImageDrawable(getResources().getDrawable(R.drawable.four));
        tvfour.setTextColor(getResources().getColor(R.color.bottom_text_normal));
    }
    public void onthreeSelected(){
        //改变底栏文字和图标颜色
        imthree.setImageDrawable(getResources().getDrawable(R.drawable.threee));
        tvthree.setTextColor(getResources().getColor(R.color.bottom_text_press));
        imtwo.setImageDrawable(getResources().getDrawable(R.drawable.twoo));
        tvtwo.setTextColor(getResources().getColor(R.color.bottom_text_normal));
        tvfour.setTextColor(getResources().getColor(R.color.bottom_text_normal));
        imfour.setImageDrawable(getResources().getDrawable(R.drawable.four));
        imone.setImageDrawable(getResources().getDrawable(R.drawable.onee));
        tvone.setTextColor(getResources().getColor(R.color.bottom_text_normal));
    }
    public void onfourSelected(){
        //改变底栏文字和图标颜色
        imfour.setImageDrawable(getResources().getDrawable(R.drawable.fourr));
        tvfour.setTextColor(getResources().getColor(R.color.bottom_text_press));
        imthree.setImageDrawable(getResources().getDrawable(R.drawable.three));
        tvthree.setTextColor(getResources().getColor(R.color.bottom_text_normal));
        imone.setImageDrawable(getResources().getDrawable(R.drawable.onee));
        tvone.setTextColor(getResources().getColor(R.color.bottom_text_normal));
        imtwo.setImageDrawable(getResources().getDrawable(R.drawable.twoo));
        tvtwo.setTextColor(getResources().getColor(R.color.bottom_text_normal));
    }
    //最小化程序
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void pop(View view) {
        hintPopupWindow.showPopupWindow(view);
    }
    private void darkenBackgroud(Float bgcolor) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgcolor;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }
}
