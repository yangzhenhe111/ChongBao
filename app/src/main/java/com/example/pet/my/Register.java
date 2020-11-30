package com.example.pet.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pet.R;


public class Register extends AppCompatActivity {
private Toolbar toolbar;
private TextView registerAgreement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setView();
    }

    private void setView() {
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
        }
    }
}