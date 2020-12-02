package com.example.pet.my;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pet.R;
import com.suke.widget.SwitchButton;


public class AcountSecurity extends AppCompatActivity {
private  ScanView view;
private  RadarView radarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount_security);
        SwitchButton switchButton = findViewById(R.id.switch_button);
        switchButton.setChecked(true);//设置为真，即默认为真
        switchButton.isChecked();//被选中
        switchButton.toggle();     //开关状态
        switchButton.toggle(false);//开关有动画
        switchButton.setShadowEffect(false);//禁用阴影效果
        switchButton.setEnabled(true);//false为禁用按钮
        switchButton.setEnableEffect(true);//false为禁用开关动画
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                Toast.makeText(AcountSecurity.this, "开关被单击", Toast.LENGTH_SHORT).show();
            }

        });


        radarView = findViewById(R.id.acount_security_radar);
        radarView.start();

    }
}