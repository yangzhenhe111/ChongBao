package com.example.pet.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
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

        }
    }
}