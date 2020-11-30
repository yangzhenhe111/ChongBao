package com.example.pet.nursing;

import android.content.Intent;
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
