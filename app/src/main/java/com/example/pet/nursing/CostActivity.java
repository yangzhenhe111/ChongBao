package com.example.pet.nursing;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pet.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        Intent i = getIntent();
        price = (String) i.getStringExtra("price");
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
            SimpleDateFormat sd = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss"); //获得下单时间
            Date date = new Date(System.currentTimeMillis());
            Usercost("Usercost" + "?username=" +  "&cakename=" + cakename + "&time=" + sd.format(date) + "&price=" + price);
        } else if (btn.getText().equals("完成")) {
            finish();
        }
    }

    public void back(View view) {
        finish();
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