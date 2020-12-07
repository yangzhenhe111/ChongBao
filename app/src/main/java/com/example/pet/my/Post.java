package com.example.pet.my;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.pet.R;
import com.example.pet.forum.MainForumTipsAdapter;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.Article;
import com.example.pet.other.entity.Tips;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Post extends AppCompatActivity {
    private List<Article> list;
    private ListView listView;
    private Toolbar toolbar;
    //private ArrayList<Tips> arrayList = new ArrayList<>();
    private MainForumTipsAdapter mainForumTipsAdapter;
    private SmartRefreshLayout smartRefreshLayout;
private Handler handler = new Handler(){
    @Override
    public void handleMessage(@NonNull Message msg) {
        switch (msg.what){
            case 1:
                mainForumTipsAdapter.notifyDataSetChanged();
                smartRefreshLayout.finishRefresh();
                break;
            case 2:
                mainForumTipsAdapter.notifyDataSetChanged();
                smartRefreshLayout.finishLoadMore();
                break;
        }
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        if (Build.VERSION.SDK_INT >= 21) {   //只有5.0及以上系统才支持，因此这里先进行了一层if判断
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE    //设置为全屏显示，必须这两行代码一起才能生效
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;  //因为背景为浅色，设置通知栏字体颜色为深色
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置为透明
        }
       // initData();
        setView();

    }

  /*  private void initData() {
        new Thread() {
            @Override
            public void run() {


            }
        }.start();
    }*/




    private void setView() {
       // arrayList.add(new Tips());
        listView = findViewById(R.id.lv_article);
        smartRefreshLayout = findViewById(R.id.srl);
        toolbar = findViewById(R.id.post_toolbar);
        mainForumTipsAdapter = new MainForumTipsAdapter(this, Cache.myPostList, R.layout.forum_tips_item);
        listView.setAdapter(mainForumTipsAdapter);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post.this.finish();
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
               // initData();
                Intent intent = new Intent(Post.this,MyDataService.class);
                startService(intent);
               Message message = new Message();
               message.what = 1;
               handler.sendMessage(message);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //initData();
                Intent intent = new Intent(Post.this,MyDataService.class);
                startService(intent);
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        });
    }


}