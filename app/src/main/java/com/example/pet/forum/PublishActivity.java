package com.example.pet.forum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pet.MainActivity;
import com.example.pet.R;

public class PublishActivity extends AppCompatActivity {

    private Button btn_back;
    private Button btn_publish;
    private EditText et_title;
    private EditText et_text;
    private LinearLayout add_image;
    private Button btn_add_topic;
    private Button btn_topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        btn_back = findViewById(R.id.btn_publish_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PublishActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_publish = findViewById(R.id.btn_publish);
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        et_title = findViewById(R.id.et_title);
        et_text = findViewById(R.id.et_text);
        add_image = findViewById(R.id.add_image);
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_add_topic = findViewById(R.id.btn_add_topic);
        btn_add_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PublishActivity.this,TopicActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_topic = findViewById(R.id.btn_topic);
    }
}
