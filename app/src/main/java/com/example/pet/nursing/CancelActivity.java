package com.example.pet.nursing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.pet.R;

public class CancelActivity extends AppCompatActivity {
    private LinearLayout shuo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);
        shuo = findViewById(R.id.shuo);
        shuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
    }

    private void pop(){
        TextView textView = new TextView(CancelActivity.this);
        textView.setText("下单时间小于三分钟免费取消订单，大于等于三分钟扣除手续费三元。");
        final PopupWindow popupWindow = new PopupWindow(textView,2*shuo.getWidth(),2*shuo.getHeight());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.box));//设置背景
        popupWindow.showAsDropDown(shuo,-shuo.getWidth()/2, -3*shuo.getHeight());
    }

    public void back(View view) {
        finish();
    }
    public void cancel(View view) {
        /*Intent i = new Intent(this,Cancel.class); 跳转到取消成功界面
        startActivity(i);*/
    }
}
