package com.example.pet.my;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
    private ArrayList<Tips> arrayList;
    private MainForumTipsAdapter mainForumTipsAdapter;
    private SmartRefreshLayout smartRefreshLayout;

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
        initData();
        setView();

    }

    private void initData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    arrayList = new ArrayList<>();
                    URL url = new URL(Cache.MY_URL + "MyTip?userId" + Cache.user.getUserId());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream input = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                    StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuffer.append(line);
                    }
                    arrayList.clear();
                    JSONArray jsonArray = new JSONArray(stringBuffer.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int post_id = jsonObject.getInt("post_id");
                        String post_title = jsonObject.getString("post_title");
                        String post_time = jsonObject.getString("post_time");
                        String post_text = jsonObject.getString("post_text");
                        String topic = jsonObject.getString("post_topic");
                        String user_name = jsonObject.getString("user_name");
                        int count_likes = jsonObject.getInt("likes");
                        int count_comments = jsonObject.getInt("comments");
                        int count_forwards = jsonObject.getInt("forwards");
                        String img_path = jsonObject.getString("picture_path");
                        String head_img_path = jsonObject.getString("user_picture_path");
                        Tips tips = new Tips();
                        tips.setId(post_id);
                        tips.setTitle(post_title);
                        tips.setText(post_text);
                        tips.setTime(post_time);
                        tips.setUserName(user_name);
                        tips.setTopic(topic);
                        tips.setLikes(count_likes);
                        tips.setComments(count_comments);
                        tips.setForwards(count_forwards);
                        tips.setImagepath(img_path);
                        tips.setHeadImagepath(head_img_path);
                        arrayList.add(tips);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }




    private void setView() {
        smartRefreshLayout = findViewById(R.id.srl);
        toolbar = findViewById(R.id.post_toolbar);
        mainForumTipsAdapter = new MainForumTipsAdapter(this, arrayList, R.layout.forum_tips_item);
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
                initData();
                mainForumTipsAdapter.notifyDataSetChanged();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                initData();
                mainForumTipsAdapter.notifyDataSetChanged();
            }
        });
    }


}