package com.example.pet.nursing;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pet.R;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.Order;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.jmessage.support.google.gson.Gson;
import cn.jmessage.support.google.gson.GsonBuilder;
import cn.jmessage.support.okhttp3.Call;
import cn.jmessage.support.okhttp3.Callback;
import cn.jmessage.support.okhttp3.MediaType;
import cn.jmessage.support.okhttp3.OkHttpClient;
import cn.jmessage.support.okhttp3.Request;
import cn.jmessage.support.okhttp3.RequestBody;
import cn.jmessage.support.okhttp3.Response;

public class CostActivity extends AppCompatActivity {
    private String price;
    private String cakename;
    private TextView tv;
    private ImageView imv;
    private Button btn;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    tv.setText("支付成功");
                    imv.setBackgroundResource(0);
                    imv.setImageResource(R.drawable.costs);
                    btn.setText("完成");
                    break;
                case 2:
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(CostActivity.this);
                    builder2.setTitle("提示");//标题
                    builder2.setMessage("交易失败");//显示的提示内容
                    builder2.setPositiveButton("确定", null);
                    AlertDialog alertDialog2 = builder2.create();
                    alertDialog2.show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);
        if (Build.VERSION.SDK_INT >= 21) {   //只有5.0及以上系统才支持，因此这里先进行了一层if判断
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE    //设置为全屏显示，必须这两行代码一起才能生效
                    |View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;  //因为背景为浅色，设置通知栏字体颜色为深色
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置为透明
        }
        Intent i = getIntent();
        price = (String) i.getStringExtra("cost");
        cakename = (String) i.getSerializableExtra("cakename");
        getviews();
        tv.setText("本次需支付" + price);
    }

    private void getviews() {
        tv = findViewById(R.id.tvpr);
        imv = findViewById(R.id.costimg);
        btn = findViewById(R.id.btncost);
    }

    public void cost(View view) {
        if (btn.getText().equals("立即支付")) {
            /*SimpleDateFormat sd = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss"); //获得下单时间
            Date date = new Date(System.currentTimeMillis());
            Usercost("Usercost" + "?username=" +  "&cakename=" + cakename + "&time=" + sd.format(date) + "&price=" + price);*/
//            tv.setText("支付成功");
//            imv.setBackgroundResource(0);
//            imv.setImageResource(R.drawable.costs);
//            btn.setText("完成");
            insert();
        } else if (btn.getText().equals("完成")) {
            finish();
        }
    }

    public void back(View view) {
        finish();
    }


    private void insert() {
        //发送数据
        SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        String    str1    =    formatter.format(curDate);
        AddressInfo.TIME=str1;
        Log.e("PlaceOrder","3");
        OkHttpClient okHttpClient = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .serializeNulls()//序列化空值
                .create();
        Order order=new Order();
        order.setOrderStart( AddressInfo.START);
        order.setOrderEnd(AddressInfo.END);
        order.setAddresser(AddressInfo.STARTPE);
        order.setClientContact(AddressInfo.STARTTEL);
        order.setAddressee(AddressInfo.ENDPE);
        order.setAddresseeContact(AddressInfo.ENDTEL);
        order.setOrderTime(AddressInfo.TIME);
        order.setKilometers(AddressInfo.DISTANCE);
        order.setOrderAmount(AddressInfo.MONEY);
        order.setRemarks(AddressInfo.BEIZHU);
        order.setIteminfo(AddressInfo.ITEMINFO);
        order.setOrderState(AddressInfo.MONEY);
        order.setUserId(Cache.user_id);
        String str = gson.toJson(order);
        Log.e("PlaceOrder","4   " + str);
        RequestBody requestBody = RequestBody.create(MediaType.parse(
                "text/plain;charset=UTF-8"),str);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(Cache.MY_URL+"addOrderServlet")/*192.168.43.86*/
                .build();
        Log.e("PlaceOrder",request.toString());
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("false:::::","a");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("true:::::::","a");
                String str = response.body().string();
                Message msg = new Message();
                if(str.equals("true")){
                    msg.what = 1;
                }else {
                    msg.what = 2;
                }
                handler.sendMessage(msg);

            }
        });
    }













    private void Usercost(final String s) {
        new Thread() {
            public void run() {
                try {
                    URL url = new URL(s);
                    url.openStream();
                    URLConnection con = url.openConnection();
                    InputStream in = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String str = reader.readLine();
                    reader.close();
                    in.close();
                    if (str != null && str.equals("下单成功")) {
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);

                    } else {
                        Message msg = new Message();
                        msg.what = 2;
                        handler.sendMessage(msg);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}