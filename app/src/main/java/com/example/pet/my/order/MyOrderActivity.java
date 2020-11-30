package com.example.pet.my.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pet.R;
import com.example.pet.other.entity.Order;
import com.example.pet.other.entity.Pet;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends AppCompatActivity {

    private ListView lvOrder;
    private MyOrderAdapter myOrderAdapter;
    private List<Order> orders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        initData();
        initMyOrderActivity();
    }

    /**
     * 初始化orders数据
     */
    private void initData() {
        orders = new ArrayList<Order>();
        for(int i = 0;i < 25;i++){
            Order order = new Order();
            order.setOrderStart("河北师范大学软件学院" + (i/6) + "0" + (i%6) + "教室");
            order.setOrderEnd("河北师范大学国培大厦" + (i/8) + "0" + (i%8) + "房间");
            order.setOrderTime("2020-04-28 11:53");
            switch (i%4){
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
            }
            Pet pet = new Pet();
            pet.setPetType("哈士奇");
            pet.setPetWeight(2);
            order.setPetInfo(pet);
            orders.add(order);
        }
        Log.e("log","1");
    }

    /**
     * 初始化MyOrderActivity
     */
    private void initMyOrderActivity() {
        lvOrder = findViewById(R.id.lv_my_order);
        Log.e("log","2");
        initMyOrderAdapter();

    }

    /**
     * 初始化adapter
     */
    private void initMyOrderAdapter() {
        myOrderAdapter = new MyOrderAdapter(this,
                orders,
                R.layout.item_my_order);
        lvOrder.setAdapter(myOrderAdapter);
        Log.e("log","3");
        lvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if("待支付".equals(orders.get(position).getOrderState())){
                    Intent intent = new Intent();
                    //跳转到待支付页面
                    startActivityForResult(intent,1);
                }
            }
        });
    }
}
