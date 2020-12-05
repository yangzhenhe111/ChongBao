package com.example.pet.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pet.R;


public class AboutUs extends AppCompatActivity {
private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        if (Build.VERSION.SDK_INT >= 21) {   //只有5.0及以上系统才支持，因此这里先进行了一层if判断
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE    //设置为全屏显示，必须这两行代码一起才能生效
                    |View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;  //因为背景为浅色，设置通知栏字体颜色为深色
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置为透明
        }
        toolbar = findViewById(R.id.about_us_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutUs.this.finish();
            }
        });
    }

    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.about_us_edition:
                Toast.makeText(this,"已是最新版本，无需更新",Toast.LENGTH_LONG).show();
                break;
            case R.id.about_us_law:
                Intent intent = new Intent(this,AboutUsLaw.class);
                startActivity(intent);
                break;
            case R.id.about_us_fun:
                Intent intent1 = new Intent(this,FunIntroduction.class);
                startActivity(intent1);
                break;
            case R.id.about_us_connect:
                Intent intent2 = new Intent(this,ConnectUs.class);
                startActivity(intent2);

        }
    }
}