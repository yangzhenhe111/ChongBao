package com.example.pet.my;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pet.R;

public class Login extends AppCompatActivity {
private TextView loginRegister;
private  TextView loginUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.login_register:
                Intent intent = new Intent(this,Register.class);
                startActivity(intent);
                break;
            case R.id.login_update:
                Intent intent1 = new Intent(this,Update.class);
                startActivity(intent1);
                break;

        }
    }
}