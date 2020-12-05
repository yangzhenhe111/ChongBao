package com.example.pet.my.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView lvOrder;
    private MyOrderAdapter myOrderAdapter;
    private List<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        toolbar = findViewById(R.id.my_order_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyOrderActivity.this.finish();
            }
        });
        //initData();
        initMyOrderActivity();
    }

    /**
     * 初始化orders数据
     */
    private void initData() {
        orders = new ArrayList<Order>();
        for (int i = 0; i < 25; i++) {
            Order order = new Order();
            order.setOrderStart("河北师范大学软件学院" + (i / 6) + "0" + (i % 6) + "教室");
            order.setOrderEnd("河北师范大学国培大厦" + (i / 8) + "0" + (i % 8) + "房间");
            order.setOrderTime("2020-04-28 11:53");
            switch (i % 5) {
                case 0:
                    order.setOrderState("已完成");
                    break;
                case 1:
                    order.setOrderState("待接单");
                    break;
                case 2:
                    order.setOrderState("已接单");
                    break;
                case 3:
                    order.setOrderState("待支付");
                    break;
                case 4:
                    order.setOrderState("已取消");
                    break;
            }
            Pet pet = new Pet();
            pet.setPetType("哈士奇");
            pet.setPetWeight("2");
            order.setPet(pet);
            orders.add(order);
        }

    }

    /**
     * 初始化MyOrderActivity
     */
    private void initMyOrderActivity() {
        lvOrder = findViewById(R.id.lv_my_order);
        //Log.e("log",Cache.myOrderList.size()+"ffff");
        // orders = Cache.myOrderList;
        initMyOrderAdapter();

    }

    /**
     * 初始化adapter
     */
    private void initMyOrderAdapter() {
        myOrderAdapter = new MyOrderAdapter(this,
                Cache.myOrderList,
                R.layout.item_my_order);
        lvOrder.setAdapter(myOrderAdapter);

        lvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("MyOrderActiviyt",position+"");
                 if ("已接单".equals(Cache.myOrderList.get(position).getOrderState())) {
                    Intent intent = new Intent();
                    intent.setClass(MyOrderActivity.this, JiedanActivity.class);
                    //跳转到待支付页面
                    startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.setClass(MyOrderActivity.this, Order2.class);
                    intent.putExtra("order", Cache.myOrderList.get(position));
                    startActivity(intent);
                }
            }
        });
    }
}
