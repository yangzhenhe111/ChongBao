package com.example.pet.forum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pet.R;
import com.example.pet.other.entity.Topic;

import java.util.ArrayList;

public class TopicActivity extends AppCompatActivity {

    private Button btn_topic_back;
    private ListView topic_list;
    private ArrayList<Topic> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        btn_topic_back = findViewById(R.id.btn_topic_back);
        btn_topic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TopicActivity.this,PublishActivity.class);
                startActivity(intent);
                finish();
            }
        });
        topic_list = findViewById(R.id.lv_topic);
        for (int i = 0; i < 10; i++) {
            Topic topic = new Topic();
            topic.setName("标签" + i);
            topic.setCount("50");
            arrayList.add(topic);
        }
        TopicAdapter topicAdapter = new TopicAdapter(this,arrayList,R.layout.topic_item);
        topic_list.setAdapter(topicAdapter);
    }
}
