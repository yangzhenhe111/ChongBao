package com.example.pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pet.chat.WeChatFragment;
import com.example.pet.forum.ForumFragment;
import com.example.pet.my.MyDataService;
import com.example.pet.my.MyFragment;
import com.example.pet.nursing.NursingFragment;
//import com.mob.MobSDK;

public class MainActivity extends AppCompatActivity {

    private Fragment forumFragment;
    private Fragment nursingFragment;
    private Fragment chatFragment;
    private Fragment myFragment;

    private TextView tvForum;
    private ImageView ivForum;
    private TextView tvNursing;
    private ImageView ivNursing;
    private TextView tvchat;
    private ImageView ivchat;
    private TextView tvMy;
    private ImageView ivMy;

    private Fragment currentFragment;

    boolean granted = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //短信授权
        //MobSDK.submitPolicyGrantResult(granted, null);
        if (Build.VERSION.SDK_INT >= 21) {   //只有5.0及以上系统才支持，因此这里先进行了一层if判断
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE    //设置为全屏显示，必须这两行代码一起才能生效
                    |View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;  //因为背景为浅色，设置通知栏字体颜色为深色
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置为透明
        }
        //添加第二条注释
        //加一条注释
        initMainActivity();
    }

    /**
     * 初始化MainActivity
     */
    private void initMainActivity() {
        currentFragment = new Fragment();
        chatFragment = new WeChatFragment();
        forumFragment = new ForumFragment();
        nursingFragment = new NursingFragment();
        myFragment = new MyFragment();

        tvForum = findViewById(R.id.tv_forum);
        ivForum = findViewById(R.id.iv_forum);

        tvNursing = findViewById(R.id.tv_nursing);
        ivNursing = findViewById(R.id.iv_nursing);

        tvchat = findViewById(R.id.tv_chat);
        ivchat = findViewById(R.id.iv_chat);

        tvMy = findViewById(R.id.tv_my);
        ivMy = findViewById(R.id.iv_my);

        initTabs();
        changeTab(myFragment);
        tvMy.setTextColor(getResources().getColor(R.color.change_tab));
        ivMy.setImageDrawable(getResources().getDrawable(R.drawable.fourr));
    }


    /**
     * 将tabs全部返回最初状态
     */
    private void initTabs() {
        tvForum.setTextColor(getResources().getColor(R.color.tab));
        ivForum.setImageDrawable(getResources().getDrawable(R.drawable.onee));
        tvNursing.setTextColor(getResources().getColor(R.color.tab));
        ivNursing.setImageDrawable(getResources().getDrawable(R.drawable.twoo));
        tvchat.setTextColor(getResources().getColor(R.color.tab));
        ivchat.setImageDrawable(getResources().getDrawable(R.drawable.three));
        tvMy.setTextColor(getResources().getColor(R.color.tab));
        ivMy.setImageDrawable(getResources().getDrawable(R.drawable.four));
    }

    /**
     * 点击tabs转换Fragment的点击事件控制器
     * @param view
     */
    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.ll_forum:
                changeTab(forumFragment);
                initTabs();
                tvForum.setTextColor(getResources().getColor(R.color.change_tab));
                ivForum.setImageDrawable(getResources().getDrawable(R.drawable.one));
            break;
            case R.id.ll_nursing:
                changeTab(nursingFragment);
                initTabs();
                tvNursing.setTextColor(getResources().getColor(R.color.change_tab));
                ivNursing.setImageDrawable(getResources().getDrawable(R.drawable.two));
                break;
            case R.id.ll_chat:
                changeTab(chatFragment);
                initTabs();
                tvchat.setTextColor(getResources().getColor(R.color.change_tab));
                ivchat.setImageDrawable(getResources().getDrawable(R.drawable.threee));
                break;
            case R.id.ll_my:
                changeTab(myFragment);
                initTabs();
                tvMy.setTextColor(getResources().getColor(R.color.change_tab));
                ivMy.setImageDrawable(getResources().getDrawable(R.drawable.fourr));
                break;
        }
    }

    /**
     * 切换fragment
     * @param fragment 将要切换进入的fragment
     */
    private void changeTab(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if(currentFragment != fragment){
            if(!fragment.isAdded()){
                transaction.add(R.id.tab_content,fragment);
            }
            transaction.hide(currentFragment);
            transaction.show(fragment);
            transaction.commit();
            currentFragment = fragment;
        }
    }


}
