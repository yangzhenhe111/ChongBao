package com.example.pet.my;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.pet.MainActivity;
import com.example.pet.R;
import com.example.pet.chat.SharedPrefHelper;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.User;

import java.util.ArrayList;

import cn.jpush.im.android.api.JMessageClient;

public class AcountManage extends AppCompatActivity {
    private ListView lvAcount;
    private ArrayList<User> list = new ArrayList<>();
    private AcountAdapter adapter;
    private LinearLayout addUser;
    private SharedPrefHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount_manage);
        helper = SharedPrefHelper.getInstance();
        initData();
        setView();

    }

    private void initData() {
        list.clear();
        for (User u : Cache.userHashSet) {
            list.add(u);
        }
        Log.e("AcountManage", list.size() + "");
    }

    private void setView() {
        Toolbar toolbar = findViewById(R.id.acount_manage_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AcountManage.this.finish();
            }

        });
        lvAcount = findViewById(R.id.acount_manage_listview);
        adapter = new AcountAdapter(list, this, R.layout.acount_item);
        lvAcount.setAdapter(adapter);
        lvAcount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cache.user = list.get(position);
                Intent intent = new Intent(AcountManage.this, MyDataService.class);
                startService(intent);
                adapter.notifyDataSetChanged();
            }

        });
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