package com.example.pet.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.pet.R;
import com.example.pet.other.entity.Article;

import java.util.ArrayList;
import java.util.List;

public class Post extends AppCompatActivity {
private List<Article> list;
private ListView listView;
private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        if (Build.VERSION.SDK_INT >= 21) {   //只有5.0及以上系统才支持，因此这里先进行了一层if判断
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE    //设置为全屏显示，必须这两行代码一起才能生效
                    |View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;  //因为背景为浅色，设置通知栏字体颜色为深色
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置为透明
        }
        initData();
        setView();
    }

    private void setView() {
        PostAdapter adapter = new PostAdapter(list,this,R.layout.article_item);
        listView = findViewById(R.id.lv_article);
        listView.setAdapter(adapter);
        toolbar = findViewById(R.id.post_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post.this.finish();
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