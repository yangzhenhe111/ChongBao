package com.example.pet.nursing;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.baidu.mapapi.utils.DistanceUtil;
import com.example.pet.R;

import java.text.DecimalFormat;

public class NeworderActivity extends AppCompatActivity {
    private RelativeLayout fei;
    private ImageView feiyong;
    private CheckBox cb;
    private TextView money;
    private LinearLayout stlin;
    private LinearLayout endlin;
    private Button cost;
    private TextView lu;
    private EditText item;
    private EditText beizhu;
    private TextView start;
    private TextView startpeople;
    private TextView startel;
    private TextView end;
    private TextView endpeople;
    private TextView endtel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neworder);
        setViews();
        setText();
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
        cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!start.getText().toString().equals("") && !startpeople.getText().toString().equals("")
                        && !startel.getText().toString().equals("") && !end.getText().toString().equals("")
                        && !endpeople.getText().toString().equals("") && !endtel.getText().toString().equals("")
                        && !item.getText().toString().equals("")) {
                    AddressInfo.START = start.getText().toString();
                    AddressInfo.STARTPE = startpeople.getText().toString();
                    AddressInfo.STARTTEL = startel.getText().toString();
                    AddressInfo.END = end.getText().toString();
                    AddressInfo.ENDPE = endpeople.getText().toString();
                    AddressInfo.ENDTEL = endtel.getText().toString();
                    AddressInfo.BEIZHU = beizhu.getText().toString();
                    AddressInfo.ITEMINFO = item.getText().toString();
                    Intent intent = new Intent(NeworderActivity.this, CostActivity.class);
                    intent.putExtra("cost", money.getText().toString());
                    startActivity(intent);
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(NeworderActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("有必填项为空");
                    builder.setPositiveButton("确定", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
        stlin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NeworderActivity.this,StartActivity.class);
                startActivity(intent);
            }
        });
        endlin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NeworderActivity.this,EndActivity.class);
                startActivity(intent);
            }
        });
        feiyong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        if(!AddressInfo.END.equals("送去哪里？") && !AddressInfo.START.equals("到哪里接？")){
            DecimalFormat df = new DecimalFormat("######0");
            String distance = df.format(DistanceUtil.getDistance(Point.END, Point.START));
            lu.setText(distance+"米");
            if(Integer.parseInt(lu.getText().toString().substring(0,lu.getText().toString().length()-1)) <= 2500){
                money.setText("15元");
            }
            else {
                int l = Integer.parseInt(lu.getText().toString().substring(0,lu.getText().toString().length()-1));
                money.setText(15 + (l / 1000) * 2 + "元");
            }
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
    private void pop(){
        LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);//自定义布局
        View pop = mLayoutInflater.inflate(R.layout.window, null, false);
        TextView qibu = (TextView) pop.findViewById(R.id.qibu);
        TextView ewai = (TextView) pop.findViewById(R.id.ewai);
        if(!lu.getText().toString().equals("")) {
            if (Integer.parseInt(lu.getText().toString().substring(0, lu.getText().toString().length() - 1)) <= 2500) {
                qibu.setText("15元");
                ewai.setText("0元");
            } else if (Integer.parseInt(lu.getText().toString().substring(0, lu.getText().toString().length() - 1)) > 2500) {
                int l = Integer.parseInt(lu.getText().toString().substring(0, lu.getText().toString().length() - 1));
                qibu.setText("15元");
                ewai.setText((l / 1000) * 2 + "元");
            }
        }
        PopupWindow popupWindow = new PopupWindow(pop, fei.getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.box));//设置背景
        popupWindow.showAsDropDown(fei,0, -3*fei.getHeight());
    }
    private void setText() {
        start.setText(AddressInfo.START);
        startpeople.setText(AddressInfo.STARTPE);
        startel.setText(AddressInfo.STARTTEL);
        end.setText(AddressInfo.END);
        endpeople.setText(AddressInfo.ENDPE);
        endtel.setText(AddressInfo.ENDTEL);
    }

    private void setViews() {
        fei = findViewById(R.id.fei);
        feiyong = findViewById(R.id.feiyong);
        stlin = findViewById(R.id.startlin);
        endlin = findViewById(R.id.endlin);
        beizhu = findViewById(R.id.beizhu);
        item = findViewById(R.id.iteminfo);
        cost = findViewById(R.id.cost);
        lu = findViewById(R.id.lu);
        cb = findViewById(R.id.cb);
        money = findViewById(R.id.money);
        start = findViewById(R.id.startadd);
        startpeople = findViewById(R.id.startpeople);
        startel = findViewById(R.id.starttel);
        end = findViewById(R.id.endadd);
        endpeople = findViewById(R.id.endpeople);
        endtel = findViewById(R.id.endtel);
    }

    private void check() {
        if(cb.isChecked()){
            cost.setEnabled(true);
            cost.setBackgroundColor(Color.parseColor("#1E90FF"));
        }
        else{
            cost.setEnabled(false);
            cost.setBackgroundColor(Color.parseColor("#808080"));
        }
    }
    public void xuzhi(View view) {
        Intent i = new Intent(this,XuzhiActivity.class);
        startActivity(i);
    }

    public void back(View view) {
        finish();
    }
}
