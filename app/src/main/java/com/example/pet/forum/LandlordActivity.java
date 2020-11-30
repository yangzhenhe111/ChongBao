package com.example.pet.forum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pet.MainActivity;
import com.example.pet.R;
import com.example.pet.other.entity.Tips;

import java.util.ArrayList;

public class LandlordActivity extends AppCompatActivity {

    private ArrayList<Tips> tipsArrayList = new ArrayList<>();
    private ListView listView;
    private Button btn_back;

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
        for (int i = 5; i < 5; i++) {
            Tips tips = new Tips();
            tips.setId(1);
            tips.setName("名字+" + i);
            tips.setTime("时间+" + i);
            tips.setTopic("标签" + i);
            tips.setTitle("标题" + i);
            tips.setText("正文" + i);
            tipsArrayList.add(tips);
        }
        LandlordTipsAdapter landlordTipsAdapter = new LandlordTipsAdapter(this, tipsArrayList, R.layout.landlord_tips_item);
        listView.setAdapter(landlordTipsAdapter);
    }
}
