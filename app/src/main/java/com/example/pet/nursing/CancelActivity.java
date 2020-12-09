package com.example.pet.nursing;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
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
        if (Build.VERSION.SDK_INT >= 21) {   //只有5.0及以上系统才支持，因此这里先进行了一层if判断
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE    //设置为全屏显示，必须这两行代码一起才能生效
                    |View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;  //因为背景为浅色，设置通知栏字体颜色为深色
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置为透明
        }
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
        textView.setText("下单后截止目前无人接单可免费取消订单。若有人接单，接单时间小于五分钟需扣除五元手续费，大于等于五分钟扣除对应订单金额的50%。");
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
