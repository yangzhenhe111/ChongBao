package com.example.pet.forum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pet.MainActivity;
import com.example.pet.R;

import org.w3c.dom.Comment;

import java.util.ArrayList;

public class TipsActivity extends AppCompatActivity {

    private ArrayList<Comment> arrayList = new ArrayList<>();
    private ListView listView;
    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        listView = findViewById(R.id.lv_comment);
        btn_back = findViewById(R.id.tips_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TipsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        CommentAdapter commentAdapter = new CommentAdapter(this, arrayList, R.layout.comment_item);
        listView.setAdapter(commentAdapter);
    }
}
