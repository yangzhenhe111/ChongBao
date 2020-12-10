package com.example.pet.nursing;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pet.R;

public class ChangehisActivity extends AppCompatActivity {
    private EditText add;
    private EditText name;
    private EditText tel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changehis);
        if (Build.VERSION.SDK_INT >= 21) {   //只有5.0及以上系统才支持，因此这里先进行了一层if判断
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE    //设置为全屏显示，必须这两行代码一起才能生效
                    |View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;  //因为背景为浅色，设置通知栏字体颜色为深色
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置为透明
        }
        add = findViewById(R.id.add);
        name = findViewById(R.id.peo);
        tel = findViewById(R.id.tel);
        Intent i = getIntent();
        add.setText(i.getStringExtra("add"));
        name.setText(i.getStringExtra("name"));
        tel.setText(i.getStringExtra("tel"));
    }

    public void sure(View view) {
        //上传信息i.getIntExtra("id")
    }

    public void back(View view) {
        finish();
    }
}
