package com.example.pet.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pet.R;
import com.example.pet.my.editinfo.EditInfo;

import java.util.ArrayList;
import java.util.List;


public class AboutUs extends AppCompatActivity {
    private Toolbar toolbar;
    private List<My> l = new ArrayList<>();
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
        My my1=new My(R.drawable.update,"版本更新",R.drawable.next);
        l.add(my1);
        My my2=new My(R.drawable.gn,"功能介绍",R.drawable.next);
        l.add(my2);
        My my3=new My(R.drawable.falv,"法律信息",R.drawable.next);
        l.add(my3);
        My my4=new My(R.drawable.dh,"联系我们",R.drawable.next);
        l.add(my4);
        MyAdapter myAdapter1 = new MyAdapter(this, R.layout.my_listview, l);
        ListView l1 = (ListView) findViewById(R.id.my_listview);
        l1.setAdapter(myAdapter1);
        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Toast.makeText(AboutUs.this,"已是最新版本，无需更新",Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Intent intent2 = new Intent(AboutUs.this,FunIntroduction.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent1 = new Intent(AboutUs.this, AboutUsLaw.class);
                        startActivity(intent1);
                        break;
                    case 3:
                        Intent intent3 = new Intent(AboutUs.this,ConnectUs.class);
                        startActivity(intent3);
                        break;
                    default:
                        break;
                }
            }
        });
        toolbar = findViewById(R.id.about_us_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutUs.this.finish();
            }
        });
    }
}