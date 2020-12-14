package com.example.pet.my.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pet.R;
import com.example.pet.my.Order2;
import com.example.pet.nursing.JiedanActivity;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.Order;
import com.example.pet.other.entity.Pet;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView lvOrder;
    private MyOrderAdapter myOrderAdapter;
    private List<Order> myOrderList;
private Handler handler = new Handler(){
    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        initMyOrderActivity();
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        if (Build.VERSION.SDK_INT >= 21) {   //只有5.0及以上系统才支持，因此这里先进行了一层if判断
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE    //设置为全屏显示，必须这两行代码一起才能生效
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;  //因为背景为浅色，设置通知栏字体颜色为深色
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置为透明
        }
        //注册事件订阅者
        EventBus.getDefault().register(this);

        toolbar = findViewById(R.id.my_order_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyOrderActivity.this.finish();
            }
        });
        //initData();
        if (Build.VERSION.SDK_INT >= 21) {   //只有5.0及以上系统才支持，因此这里先进行了一层if判断
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE    //设置为全屏显示，必须这两行代码一起才能生效
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;  //因为背景为浅色，设置通知栏字体颜色为深色
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置为透明
        }
        toolbar = findViewById(R.id.my_order_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyOrderActivity.this.finish();
            }
        });
         initData();

    }

    /**
     * 初始化orders数据
     */
    private void initData() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    if (myOrderList != null) {
                        myOrderList.clear();
                    } else {
                        myOrderList = new ArrayList<>();
                    }
                    URL url = new URL(Cache.MY_URL + "MyOrder?userId=" + Cache.user.getUserId());
                    InputStream in = url.openStream();
                    StringBuilder str = new StringBuilder();
                    byte[] bytes = new byte[256];
                    int len = 0;
                    while ((len = in.read(bytes)) != -1) {
                        str.append(new String(bytes, 0, len, "utf-8"));

                    }

                    in.close();
                    JSONArray jsonArray = new JSONArray(str.toString());
                    Log.e("", jsonArray.toString());
                    myOrderList = new ArrayList();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject rs = jsonArray.getJSONObject(i);
                        Order order = new Gson().fromJson(rs.toString(), Order.class);
                        int id = rs.getInt("petId");
                        URL url1 = new URL(Cache.MY_URL + "GetPet?petId="+ id);
                        InputStream in1 = url1.openStream();
                        StringBuilder str1 = new StringBuilder();
                        byte[] bytes1 = new byte[256];
                        int len1 = 0;
                        while ((len1 = in1.read(bytes1)) != -1) {
                            str1.append(new String(bytes1, 0, len1, "utf-8"));

                        }
                        Pet pet = new Gson().fromJson(str1.toString(), Pet.class);
                        order.setPet(pet);
                        Log.e("MyDataService", order.toString());
                        myOrderList.add(order);
                    }
                    Message message = new Message();
                    handler.sendMessage(message);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }

    /**
     * 初始化MyOrderActivity
     */
    private void initMyOrderActivity() {
        lvOrder = findViewById(R.id.lv_my_order);

        initMyOrderAdapter();

    }

    //当取消订单，EventBus更新视图
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        if (event.equals("更新")) {
            myOrderAdapter.notifyDataSetChanged();
        }
    }

    ;


    /**
     * 初始化adapter
     */
    private void initMyOrderAdapter() {
        myOrderAdapter = new MyOrderAdapter(this,
               myOrderList,
                R.layout.item_my_order);
        lvOrder.setAdapter(myOrderAdapter);

        lvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("MyOrderActiviyt", position + "");
                if ("已接单".equals(myOrderList.get(position).getOrderState())) {
                    Intent intent = new Intent();
                    intent.setClass(MyOrderActivity.this, JiedanActivity.class);
                    //跳转到待支付页面
                    startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.setClass(MyOrderActivity.this, Order2.class);
                    intent.putExtra("index", myOrderList.get(position));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
