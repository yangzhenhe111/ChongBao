package com.example.pet;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pet.forum.ForumFragment;
import com.example.pet.my.MyFragment;
import com.example.pet.nursing.NursingFragment;

public class MainActivity extends AppCompatActivity {

    private LinearLayout llForum;
    private LinearLayout llNursing;
    private LinearLayout llMy;
    private Fragment forumFragment;
    private Fragment nursingFragment;
    private Fragment myFragment;
    private TextView tvForum;
    private ImageView ivForum;
    private TextView tvNursing;
    private ImageView ivNursing;
    private TextView tvMy;
    private ImageView ivMy;

    private Fragment currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMainActivity();
    }

    /**
     * 初始化MainActivity
     */
    private void initMainActivity() {
        currentFragment = new Fragment();

        forumFragment = new ForumFragment();
        nursingFragment = new NursingFragment();
        myFragment = new MyFragment();
        llForum = findViewById(R.id.ll_forum);
        tvForum = findViewById(R.id.tv_forum);
        ivForum = findViewById(R.id.iv_forum);
        llNursing = findViewById(R.id.ll_nursing);
        tvNursing = findViewById(R.id.tv_nursing);
        ivNursing = findViewById(R.id.iv_nursing);
        llMy = findViewById(R.id.ll_my);
        tvMy = findViewById(R.id.tv_my);
        ivMy = findViewById(R.id.iv_my);
        initTabs();
        changeTab(forumFragment);
        tvForum.setTextColor(getResources().getColor(R.color.change_tab));
        ivForum.setImageDrawable(getResources().getDrawable(R.drawable.forum2));
    }


    /**
     * 将tabs全部返回最初状态
     */
    private void initTabs() {
        tvForum.setTextColor(getResources().getColor(R.color.tab));
        ivForum.setImageDrawable(getResources().getDrawable(R.drawable.forum1));
        tvNursing.setTextColor(getResources().getColor(R.color.tab));
        ivNursing.setImageDrawable(getResources().getDrawable(R.drawable.nursing1));
        tvMy.setTextColor(getResources().getColor(R.color.tab));
        ivMy.setImageDrawable(getResources().getDrawable(R.drawable.my1));
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
                ivForum.setImageDrawable(getResources().getDrawable(R.drawable.forum2));
            break;
            case R.id.ll_nursing:
                changeTab(nursingFragment);
                initTabs();
                tvNursing.setTextColor(getResources().getColor(R.color.change_tab2));
                ivNursing.setImageDrawable(getResources().getDrawable(R.drawable.nursing2));
                break;
            case R.id.ll_my:
                changeTab(myFragment);
                initTabs();
                tvMy.setTextColor(getResources().getColor(R.color.change_tab));
                ivMy.setImageDrawable(getResources().getDrawable(R.drawable.my2));

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
