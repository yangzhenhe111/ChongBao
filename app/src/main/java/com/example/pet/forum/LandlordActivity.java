package com.example.pet.forum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pet.MainActivity;
import com.example.pet.R;
import com.example.pet.other.entity.Tips;

import java.util.ArrayList;

public class LandlordActivity extends AppCompatActivity {

    private ArrayList<Tips> tipsArrayList = new ArrayList<>();
    private ListView listView;
    private LinearLayout btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord);
        btn_back = findViewById(R.id.btn_landlord_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LandlordActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        listView = findViewById(R.id.lv_landlord_tips);
        LandlordTipsAdapter landlordTipsAdapter = new LandlordTipsAdapter(this, tipsArrayList, R.layout.landlord_tips_item);
        listView.setAdapter(landlordTipsAdapter);
    }
}
