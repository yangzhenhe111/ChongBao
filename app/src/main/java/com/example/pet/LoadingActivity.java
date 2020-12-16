package com.example.pet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.pet.chat.SharedPrefHelper;
import com.example.pet.my.Login;
import com.example.pet.my.MyUserService;
import com.example.pet.nursing.StartActivity;
import com.example.pet.other.Cache;
import com.example.pet.other.MainActivity;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;


public class LoadingActivity extends AppCompatActivity {
    SharedPrefHelper helper;

    private void initView() {
        helper = SharedPrefHelper.getInstance();
        final Handler handler = new Handler();
        JAnalyticsInterface.onPageStart(this,this.getClass().getCanonicalName());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (helper.getUserPW().equals("")) {
                    Intent intent = new Intent(LoadingActivity.this, Start1Activity.class);
                    startActivity(intent);
                    finish();
                }else{
                    JMessageClient.login(helper.getUserId(), helper.getUserPW(), new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if (i==0){
                                ToastUtils.showShort("自动登录成功");
                                initUserInfo();
                                Intent intent = new Intent(getApplication(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                LoadingActivity.this.finish();
                            }else {
                                startActivity(new Intent(LoadingActivity.this, Login.class));
                                ToastUtils.showShort("登录失败:"+s);
                            }
                        }
                    });
                }
            }
        }, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JAnalyticsInterface.onPageEnd(this,this.getClass().getCanonicalName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {   //只有5.0及以上系统才支持，因此这里先进行了一层if判断
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE    //设置为全屏显示，必须这两行代码一起才能生效
                    |View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;  //因为背景为浅色，设置通知栏字体颜色为深色
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置为透明
        }
        setContentView(R.layout.activity_loading);
        BarUtils.setNotificationBarVisibility(false);
        BarUtils.setStatusBarVisibility(this,false);
        initView();
    }

    public void initUserInfo(){
        JMessageClient.getUserInfo(helper.getUserId(), new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                if (i==0) {
                    Cache.userPhone = userInfo.getUserName();
                    Intent intent2 = new Intent(LoadingActivity.this, MyUserService.class);
                    startService(intent2);
                    helper.setNakeName(userInfo.getNickname());
                    helper.setUserId(userInfo.getUserName());
                }
            }
        });
    }
}
