package com.example.pet.my;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.pet.R;
import com.example.pet.other.entity.Order;

public class Order2 extends AppCompatActivity {
    private LinearLayout statusPay;
    private LinearLayout statusRunner;
    private LinearLayout statusCancel;
    private LinearLayout statusComplete;
    private LinearLayout statusUncomplete;
    private LinearLayout orderDeatil;
    private Toolbar toolbar;
    private TextView toolbarTitle;

    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order2);
        setView();
        order = new Order();
        order.setOrderState("待接单");
        setStatus();

    }

    private void setStatus() {
        switch (order.getOrderState()) {
            case "已取消":
                toolbarTitle.setText("已取消");
                statusComplete.setVisibility(View.GONE);
                statusCancel.setVisibility(View.VISIBLE);
                statusRunner.setVisibility(View.INVISIBLE);
                statusPay.setVisibility(View.INVISIBLE);
                break;
            case "待支付":
                toolbarTitle.setText("待支付");
                statusComplete.setVisibility(View.GONE);
                statusCancel.setVisibility(View.INVISIBLE);
                statusRunner.setVisibility(View.INVISIBLE);
                statusPay.setVisibility(View.VISIBLE);
                break;
            case "待接单":
                toolbarTitle.setText("待接单");
                statusComplete.setVisibility(View.GONE);
                statusCancel.setVisibility(View.INVISIBLE);
                statusRunner.setVisibility(View.VISIBLE);
                statusPay.setVisibility(View.INVISIBLE);
                break;
            case "已完成":
                toolbarTitle.setText("已完成");
                statusComplete.setVisibility(View.VISIBLE);
                statusUncomplete.setVisibility(View.GONE);
                break;
        }
    }

    private void setView() {
        orderDeatil = findViewById(R.id.order_count_detail1);
        toolbarTitle =findViewById(R.id.order_toolbar_title);
        statusPay = findViewById(R.id.order_wait_pay);
        statusRunner = findViewById(R.id.order_wait_runner);
        statusCancel = findViewById(R.id.order_status_cancel);
        statusComplete = findViewById(R.id.order_status_complete);
        statusUncomplete = findViewById(R.id.order_status_uncomplete);
        toolbar = findViewById(R.id.order_toolbar);
        setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order2.this.finish();
            }
        });
    }

    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.order_btn_cancel:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("确定取消订单");
                builder.setPositiveButton("确定取消", null);
                builder.setNegativeButton("再等等", null);
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.order_count_detail:
                orderDeatil.setVisibility(View.VISIBLE);
                break;
        }
    }
}