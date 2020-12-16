package com.example.pet.my;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.pet.R;
import com.example.pet.chat.BaseApplication;
import com.example.pet.chat.SharedPrefHelper;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.User;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.ArrayList;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.IntegerCallback;
import cn.jpush.im.api.BasicCallback;

import static cn.jpush.im.android.api.JMessageClient.FLAG_NOTIFY_SILENCE;
import static cn.jpush.im.android.api.JMessageClient.FLAG_NOTIFY_WITH_SOUND;
import static cn.jpush.im.android.api.JMessageClient.FLAG_NOTIFY_WITH_VIBRATE;

public class AcountManage extends AppCompatActivity {
    private ListView lvAcount;
    @BindView(R.id.setting_noisy)
    SwitchButton mSettingNoisy;
    @BindView(R.id.setting_roaming)
    SwitchButton mSettingRoaming;
    @BindView(R.id.setting_push_vib)
    SwitchButton mSettingPushVib;
    @BindView(R.id.setting_push_led)
    SwitchButton mSettingPushLed;
    private ArrayList<User> list = new ArrayList<>();
    private AcountAdapter adapter;
    private LinearLayout addUser;
    private SharedPrefHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount_manage);
        if (Build.VERSION.SDK_INT >= 21) {   //只有5.0及以上系统才支持，因此这里先进行了一层if判断
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE    //设置为全屏显示，必须这两行代码一起才能生效
                    |View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;  //因为背景为浅色，设置通知栏字体颜色为深色
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置为透明
        }
        helper = SharedPrefHelper.getInstance();
        initData();
        setView();
        initSwitchOnClick();
    }
    private void initSwitchOnClick() {
        /*免打扰*/
        JMessageClient.getNoDisturbGlobal(new IntegerCallback() {
            @Override
            public void gotResult(int i, String s, Integer integer) {
//                Log.e("disturb===", "" + i + "," + s + "," + integer);
                if (integer == 1) {
                    mSettingNoisy.setChecked(true);
                } else {
                    mSettingNoisy.setChecked(false);
                }
            }
        });

        mSettingNoisy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    disturbGlobal(1);
                } else {
                    disturbGlobal(0);
                }
            }
        });

        /*消息漫游,默认开启状态*/
        mSettingRoaming.setChecked(helper.getRoaming());
        mSettingRoaming.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    JMessageClient.init(BaseApplication.baseApplication, true);
                } else {
                    JMessageClient.init(BaseApplication.baseApplication, false);
                }
                helper.setRoaming(b);
            }
        });

        pushVib();
    }
    /*震动*/
    private void pushVib() {
        mSettingPushVib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (helper.getMusic()) {
                        JMessageClient.setNotificationFlag(FLAG_NOTIFY_SILENCE | FLAG_NOTIFY_WITH_SOUND | FLAG_NOTIFY_WITH_VIBRATE);
                    } else {
                        JMessageClient.setNotificationFlag(FLAG_NOTIFY_SILENCE | FLAG_NOTIFY_WITH_VIBRATE);
                    }
                } else {
                    if (helper.getMusic()) {
                        JMessageClient.setNotificationFlag(FLAG_NOTIFY_SILENCE | FLAG_NOTIFY_WITH_SOUND);
                    } else {
                        JMessageClient.setNotificationFlag(FLAG_NOTIFY_SILENCE);
                    }
                }
                helper.setVib(b);
            }
        });
    }

    /*全局免打扰*/
    private void disturbGlobal(int i) {
        JMessageClient.setNoDisturbGlobal(i, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {

            }
        });
    }
    private void initData() {
        list.clear();
        for (User u : Cache.userHashSet) {
            list.add(u);
        }
    }

    private void setView() {
        Toolbar toolbar = findViewById(R.id.acount_manage_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AcountManage.this.finish();
            }

        });
        //lvAcount = findViewById(R.id.acount_manage_listview);
        adapter = new AcountAdapter(list, this, R.layout.acount_item);
        /*lvAcount.setAdapter(adapter);
        lvAcount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cache.user = list.get(position);
                Intent intent = new Intent(AcountManage.this, MyDataService.class);
                startService(intent);
                adapter.notifyDataSetChanged();
            }

        });*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
        adapter.notifyDataSetChanged();
    }

    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.acount_manage_adduser:
                Intent intent = new Intent(AcountManage.this, Login.class);
                startActivity(intent);
                break;
            case R.id.acount_manage_loginout:
                JMessageClient.logout();
                helper.setUserPW("");
                helper.setNakeName("");
                Intent intent2 = new Intent("android.intent.action.CART_BROADCAST");
                intent2.putExtra("data","finish");
                LocalBroadcastManager.getInstance(AcountManage.this).sendBroadcast(intent2);
                AcountManage.this.sendBroadcast(intent2);
                Intent intent1 = new Intent(AcountManage.this,Login.class);
                startActivity(intent1);
                AcountManage.this.finish();
                break;
        }
    }
}