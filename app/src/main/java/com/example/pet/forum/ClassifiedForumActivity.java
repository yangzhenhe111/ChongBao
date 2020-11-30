package com.example.pet.forum;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pet.R;
import com.example.pet.other.entity.Tips;

import java.util.ArrayList;

public class ClassifiedForumActivity extends AppCompatActivity {

    private EditText et_classified_search;
    private Button btn_classified_search;
    private Button btn_classified_publish;
    private TextView tv_classified_newest;
    private TextView tv_classified_hot;
    private TextView tv_classified_essence;
    private ListView lv_tips;
    private ArrayList<Tips> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classified_forum);
        et_classified_search = findViewById(R.id.et_classified_search);
        btn_classified_search = findViewById(R.id.btn_classified_search);
        btn_classified_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_classified_publish = findViewById(R.id.btn_classified_publish_articles);
        btn_classified_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ClassifiedForumActivity.this,PublishActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tv_classified_newest = findViewById(R.id.classified_newest);
        TextPaint base = tv_classified_newest.getPaint();
        base.setFakeBoldText(true);
        tv_classified_hot = findViewById(R.id.classified_hot);
        tv_classified_essence = findViewById(R.id.classified_essence);
        tv_classified_newest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_classified_newest.setBackgroundColor(getResources().getColor(R.color.Blue));
                tv_classified_hot.setBackgroundColor(getResources().getColor(R.color.White));
                tv_classified_essence.setBackgroundColor(getResources().getColor(R.color.White));
                TextPaint newest = tv_classified_newest.getPaint();
                newest.setFakeBoldText(true);
                TextPaint hot = tv_classified_hot.getPaint();
                hot.setFakeBoldText(false);
                TextPaint essence = tv_classified_essence.getPaint();
                essence.setFakeBoldText(false);
            }
        });
        tv_classified_hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_classified_newest.setBackgroundColor(getResources().getColor(R.color.White));
                tv_classified_hot.setBackgroundColor(getResources().getColor(R.color.Orange));
                tv_classified_essence.setBackgroundColor(getResources().getColor(R.color.White));
                TextPaint newest = tv_classified_newest.getPaint();
                newest.setFakeBoldText(false);
                TextPaint hot = tv_classified_hot.getPaint();
                hot.setFakeBoldText(true);
                TextPaint essence = tv_classified_essence.getPaint();
                essence.setFakeBoldText(false);
            }
        });
        tv_classified_essence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_classified_newest.setBackgroundColor(getResources().getColor(R.color.White));
                tv_classified_hot.setBackgroundColor(getResources().getColor(R.color.White));
                tv_classified_essence.setBackgroundColor(getResources().getColor(R.color.Purple));
                TextPaint newest = tv_classified_newest.getPaint();
                newest.setFakeBoldText(false);
                TextPaint hot = tv_classified_hot.getPaint();
                hot.setFakeBoldText(false);
                TextPaint essence = tv_classified_essence.getPaint();
                essence.setFakeBoldText(true);
            }
        });
        lv_tips = findViewById(R.id.lv_classified_articles);
        for (int i = 0; i < 5; i++) {
            Tips tips = new Tips();
            tips.setId(1);
            tips.setName("名字+" + i);
            tips.setTime("时间+" + i);
            tips.setTopic("标签" + i);
            tips.setTitle("标题" + i);
            tips.setText("正文" + i);
            arrayList.add(tips);
        }
        ClassfiedForumAdapter classfiedForumAdapter = new ClassfiedForumAdapter(this,arrayList,R.layout.forum_tips_item);
        lv_tips.setAdapter(classfiedForumAdapter);
    }
    public void search(){
        new Thread(){
            @Override
            public void run() {
                String search = et_classified_search.getText().toString();
            }
        };
    }
}
