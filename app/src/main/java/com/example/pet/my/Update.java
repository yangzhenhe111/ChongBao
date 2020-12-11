package com.example.pet.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.pet.R;
import com.example.pet.my.editinfo.EditInfo;

public class Update extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText etPhone;
    private EditText etYan;
    private EditText etPassword;

    private String phone;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        setView();
    }

    private void setView() {
        toolbar = findViewById(R.id.find_toolbar);
        etPhone = findViewById(R.id.et_phone_find);
        etYan = findViewById(R.id.et_yanzheng_find);
        etPassword = findViewById(R.id.et_pass_find);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update.this.finish();
            }
        });
    }

    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.btn_getYan_find:
                //获取验证码
                break;
            case R.id.btn_sub_find:
                //验证验证码，并提交新的密码
                break;
        }
    }
}