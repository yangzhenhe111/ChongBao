package com.example.pet.nursing;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;

import com.example.pet.R;

public class NeworderActivity extends AppCompatActivity {
    private RelativeLayout fei;
    private ImageView feiyong;
    private CheckBox cb;
    private LinearLayout stlin;
    private LinearLayout endlin;
    private TextView money;
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
                AddressInfo.START = start.getText().toString();
                AddressInfo.STARTPE = startpeople.getText().toString();
                AddressInfo.STARTTEL = startel.getText().toString();
                AddressInfo.END = end.getText().toString();
                AddressInfo.ENDPE = endpeople.getText().toString();
                AddressInfo.ENDTEL = endtel.getText().toString();
                AddressInfo.BEIZHU = beizhu.getText().toString();
                AddressInfo.ITEMINFO = item.getText().toString();
                Intent intent = new Intent(NeworderActivity.this,CostActivity.class);
                startActivityForResult(intent, 66);
            }
        });
        stlin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NeworderActivity.this,StartActivity.class);
                startActivityForResult(intent, 99);
            }
        });
        endlin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NeworderActivity.this,EndActivity.class);
                startActivityForResult(intent, 88);
            }
        });
        feiyong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 99 && resultCode == 999){
            finish();
            Intent intent = new Intent(this, NeworderActivity.class);
            startActivity(intent);
        }
        else if(requestCode == 88 && resultCode == 888){
            finish();
            Intent intent = new Intent(this, NeworderActivity.class);
            startActivity(intent);
        }
    }
    private void pop(){
        LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);//自定义布局
        LinearLayout lin = (LinearLayout) mLayoutInflater.inflate(R.layout.window, null, true);
        PopupWindow popupWindow = new PopupWindow(lin, fei.getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT, true);
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

    }

    public void back(View view) {
        finish();
    }
}
