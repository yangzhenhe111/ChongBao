package com.example.pet.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.example.pet.R;
import com.example.pet.chat.BaseApplication;
import com.example.pet.chat.SharedPrefHelper;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.IntegerCallback;
import cn.jpush.im.api.BasicCallback;

import static cn.jpush.im.android.api.JMessageClient.FLAG_NOTIFY_SILENCE;
import static cn.jpush.im.android.api.JMessageClient.FLAG_NOTIFY_WITH_SOUND;
import static cn.jpush.im.android.api.JMessageClient.FLAG_NOTIFY_WITH_VIBRATE;

public class Setting extends AppCompatActivity {
    private SharedPrefHelper helper;
    @BindView(R.id.setting_noisy)
    SwitchButton mSettingNoisy;
    @BindView(R.id.setting_roaming)
    SwitchButton mSettingRoaming;
    @BindView(R.id.setting_push_vib)
    SwitchButton mSettingPushVib;
    @BindView(R.id.setting_push_music)
    SwitchButton mSettingPushMusic;
    @BindView(R.id.setting_push_led)
    SwitchButton mSettingPushLed;
    private Toolbar toolbar;
    private List<My> myList1 = new ArrayList<>();
    private List<My> myList2 = new ArrayList<>();
    private List<My> myList3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //获得控件
        setView();
        //生成数据
        initData();
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


        pushMusic();
        pushVib();
    }

    /*提示音*/
    private void pushMusic() {
        mSettingPushMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (helper.getVib()) {
                        JMessageClient.setNotificationFlag(FLAG_NOTIFY_SILENCE | FLAG_NOTIFY_WITH_SOUND | FLAG_NOTIFY_WITH_VIBRATE);
                    } else {
                        JMessageClient.setNotificationFlag(FLAG_NOTIFY_SILENCE | FLAG_NOTIFY_WITH_SOUND);
                    }
                    helper.setMusic(true);
                } else {
                    if (helper.getVib()) {
                        JMessageClient.setNotificationFlag(FLAG_NOTIFY_SILENCE | FLAG_NOTIFY_WITH_VIBRATE);
                    } else {
                        JMessageClient.setNotificationFlag(FLAG_NOTIFY_SILENCE);
                    }
                    helper.setMusic(false);
                }
            }
        });
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
        My my1 = new My(R.drawable.acount, "账号管理", R.drawable.next);
        myList1.add(my1);
        My my2 = new My(R.drawable.address, "地址管理", R.drawable.next);
        myList2.add(my2);
        My my3 = new My(R.drawable.pay, "支付设置", R.drawable.next);
        myList3.add(my3);
    }

    private void setView() {
        toolbar = findViewById(R.id.setting_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.this.finish();
            }
        });
        //适配第一个listView
        MyAdapter myAdapter1 = new MyAdapter(this, R.layout.my_listview, myList1);
        ListView listView1 = (ListView) findViewById(R.id.setting_listview1);
        listView1.setAdapter(myAdapter1);
        //适配第二个listView
        MyAdapter myAdapter2 = new MyAdapter(this, R.layout.my_listview, myList2);
        ListView listView2 = (ListView) findViewById(R.id.setting_listview2);
        listView2.setAdapter(myAdapter2);
        //适配第三个listView
        MyAdapter myAdapter3 = new MyAdapter(this, R.layout.my_listview, myList3);
        ListView listView3 = (ListView) findViewById(R.id.setting_listview3);
        listView3.setAdapter(myAdapter3);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(Setting.this,AcountManage.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}