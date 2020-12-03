package com.example.pet.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.opengl.ETC1;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet.R;


public class Register extends AppCompatActivity {
private Toolbar toolbar;
private TextView registerAgreement;
private TextView tvLogin;
private EditText etPhone;
private CheckBox cbXieYi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setView();
    }

    private void setView() {
        etPhone = findViewById(R.id.et_phone);
        cbXieYi = findViewById(R.id.cb_xieyi);
        toolbar = findViewById(R.id.register_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register.this.finish();
            }
        });
    }

    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.register_agreement:
                Intent intent = new Intent(this,Agreement.class);
                startActivity(intent);
                break;
            case R.id.tv_login:
                Register.this.finish();
                break;
            case R.id.btn_next1:
                String str = etPhone.getText().toString();
                if(str.length() == 11  ){
                    if( cbXieYi.isChecked()){
                    Intent intent1 = new Intent(this,SetPassword.class);
                    intent1.putExtra("phone",str);
                    startActivity(intent1);
                    }else{
                        Toast.makeText(this,"请同意“用户协议”",Toast.LENGTH_LONG).show();
                    }
                }else {
                    //提示位数错误
                    Toast.makeText(this,"号码格式错误",Toast.LENGTH_LONG).show();
                }


        }
    }
}