package com.example.pet.my;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.pet.MainActivity;
import com.example.pet.R;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.Order;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Order2 extends AppCompatActivity {
    private LinearLayout statusPay;
    private LinearLayout statusRunner;
    private LinearLayout statusCancel;
    private LinearLayout statusComplete;
    private LinearLayout statusUncomplete;
    private LinearLayout orderDeatil;
    private LinearLayout orderExtra;
    private Toolbar toolbar;

    private TextView finishCount;
    private TextView totalCount;
    private TextView countDetail;
    private TextView countStart;
    private TextView countExtra;
    private TextView orderTime;
    private TextView orderStart;
    private TextView orderEnd;
    private TextView orderAddressee;
    private TextView orderAddresser;
    private TextView orderRemark;
    private TextView orderPet;
    private TextView toolbarTitle;
    private TextView orderFinishStart;
    private TextView orderFinishAddressee;
    private TextView orderFinishEnd;
    private TextView orderFinishAddresser;
    private TextView orderFinishPet;
    private TextView orderFinishRemark;
    private TextView orderFinishRunner;
    private TextView finishId;
    private TextView finishTime;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order2);


        setView();
        Intent intent = getIntent();
        int index = intent.getIntExtra("index",0);
        order = Cache.myOrderList.get(index);
        Log.e("order", "4");
//        order = new Order();
//        order.setOrderState("待接单");
        setStatus();

    }

    private void setStatus() {
        changeView();
        int count = Integer.parseInt(order.getOrderAmount().trim());
        totalCount.setText(count + "元");
        if (count > 15) {
            countExtra.setText((count - 15) + "元");
            orderExtra.setVisibility(View.VISIBLE);
        }
        orderTime.setText(order.getOrderTime());
        orderFinishRemark.setText(order.getRemarks());
        orderRemark.setText(order.getRemarks());
        orderAddresser.setText(order.getAddresser() + "  " + order.getPetShopContact());
        orderFinishAddresser.setText(order.getAddresser() + "  " + order.getPetShopContact());
        orderFinishAddressee.setText(order.getAddressee() + " " + order.getClientContact());
        orderAddressee.setText(order.getAddressee() + " " + order.getClientContact());
        orderPet.setText(order.getPet().getPetName() + order.getPet().getPetWeight());
        orderFinishPet.setText(order.getPet().getPetName() + order.getPet().getPetWeight());
        orderStart.setText(order.getOrderStart());
        orderFinishStart.setText(order.getOrderStart());
        orderEnd.setText(order.getOrderEnd());
        orderFinishEnd.setText(order.getOrderEnd());
        orderFinishRunner.setText(order.getRunnerName());
        finishId.setText(order.getOrderId() + "");
        finishTime.setText(order.getOrderTime());
        finishCount.setText(count + "元");
    }

    private void setView() {
        finishCount = findViewById(R.id.order_finish_count);
        finishTime = findViewById(R.id.order_finish_time);
        finishId = findViewById(R.id.order_finish_id);
        orderFinishRunner = findViewById(R.id.order_finish_runner);
        orderExtra = findViewById(R.id.order_count_extra1);
        orderFinishRemark = findViewById(R.id.order_finish_remark);
        orderFinishPet = findViewById(R.id.order_finish_end);
        orderFinishAddresser = findViewById(R.id.order_finish_addrresser);
        orderFinishEnd = findViewById(R.id.order_finish_end);
        orderFinishStart = findViewById(R.id.order_finish_start);
        orderFinishAddressee = findViewById(R.id.order_finish_addrressee);
        countDetail = findViewById(R.id.order_count_detail);
        totalCount = findViewById(R.id.order_total_count);

        countStart = findViewById(R.id.order_count_start);
        countExtra = findViewById(R.id.order_count_extra);
        orderTime = findViewById(R.id.order_time);
        orderStart = findViewById(R.id.order_start);
        orderEnd = findViewById(R.id.order_end);
        orderAddressee = findViewById(R.id.order_addressee);
        orderAddresser = findViewById(R.id.order_addresser);
        orderRemark = findViewById(R.id.order_remark);
        orderPet = findViewById(R.id.order_pet);
        orderDeatil = findViewById(R.id.order_count_detail1);
        toolbarTitle = findViewById(R.id.order_toolbar_title);
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
                orderToCancel();
                break;
            case R.id.order_btn_cancel1:
                orderToCancel();
                break;
            case R.id.order_count_detail:

                if (countDetail.getText().toString().equals("展开详情")) {

                    countDetail.setText("收起详情");
                    orderDeatil.setVisibility(View.VISIBLE);
                } else if (countDetail.getText().toString().equals("收起详情")) {
                    countDetail.setText("展开详情");
                    Log.e("Order2", "展开");
                    orderDeatil.setVisibility(View.GONE);
                }

                break;
            case R.id.order_start_connect:
                callStart();
                break;
            case R.id.order_end_connect:
                callEnd();
                break;
            case R.id.tv_pay_now:
                //跳转支付页面
                break;
            case R.id.order_readd:
                newOrder();
                break;
            case R.id.order_readd1:
                newOrder();
                break;
            case R.id.order_finish_end_connect:
                callEnd();
                break;


        }

    }

    private void changeState() {


        new Thread() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL(Cache.MY_URL+ "ChangeState?state=已取消&orderId=" +order.getOrderId());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    InputStream inputStream = conn.getInputStream();
                    inputStream.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        order.setOrderState("已取消");
        for (int i=0;i<Cache.myOrderList.size();i++){
            if(Cache.myOrderList.get(i).getOrderId()==order.getOrderId()){
                Cache.myOrderList.get(i).setOrderState("已取消");
            }
        }
        EventBus.getDefault().post("更新");
        changeView();
    }

    public void changeView() {
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

    //取消订单
    private void orderToCancel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定取消订单");
        builder.setPositiveButton("确定取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeState();
            }
        });


        builder.setNegativeButton("再等等", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //重新下单
    private void newOrder() {
        Intent intent2 = new Intent(this, MainActivity.class);
        startActivity(intent2);
    }

    //致电起点
    private void callStart() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + order.getClientContact()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void callEnd() {
        Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + order.getPetShopContact()));
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}