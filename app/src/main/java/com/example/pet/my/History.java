package com.example.pet.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.pet.R;
import com.example.pet.other.entity.Article;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    private List<Article> list;
    private ListView listView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initData();
        setView();
    }
    private void setView() {
        PostAdapter adapter = new PostAdapter(list,this,R.layout.article_item);
        listView = findViewById(R.id.lv_history_article);
        listView.setAdapter(adapter);
        toolbar = findViewById(R.id.history_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                History.this.finish();
            }
        });
    }

    private void initData() {
        list = new ArrayList<>();
        for(int i=0;i<5;i++){
            Article article = new Article();
            article.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.article));
            article.setTitle("标题");
            article.setContent("我很喜欢我家的猫。天气冷时就会依偎在我身边，给我暖手");
            list.add(article);
        }
    }
}