package com.example.pet.my;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pet.R;
import com.example.pet.forum.MainForumTipsAdapter;
import com.example.pet.forum.New_post_detail;
import com.example.pet.forum.RecyAdapter;
import com.example.pet.my.editinfo.EditAddress;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.Article;
import com.example.pet.other.entity.Tips;
import com.rohit.recycleritemclicksupport.RecyclerItemClickSupport;
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
    private Toolbar toolbar;
    private EditText etContent;
    private ArrayList<Tips> myPostList;
    private RecyclerView recyclerView;
    private RecyAdapter recyAdapter;
    private SmartRefreshLayout smartRefreshLayout;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    recyAdapter.notifyDataSetChanged();
                    smartRefreshLayout.finishRefresh();
                    break;
                case 2:
                    recyAdapter.notifyDataSetChanged();
                    smartRefreshLayout.finishLoadMore();
                    break;
                case 3:
                    recyAdapter = new RecyAdapter(Post.this, myPostList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Post.this);
                    linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(recyAdapter);
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
        initData();
        setView();
        RecyclerItemClickSupport.addTo(recyclerView).setOnItemClickListener(new RecyclerItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                Intent intent = new Intent(Post.this, New_post_detail.class);
                intent.putExtra("id", myPostList.get(i).getId());
                startActivity(intent);
            }
        });
    }

    private void initData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    if (myPostList != null) {
                        myPostList.clear();
                    } else {
                        myPostList = new ArrayList<>();
                    }
                    URL url = new URL(Cache.MY_URL + "MyTip?userId=" + Cache.user.getUserId());
                    InputStream in = url.openStream();
                    StringBuilder str = new StringBuilder();
                    byte[] bytes = new byte[256];
                    int len = 0;
                    while ((len = in.read(bytes)) != -1) {
                        str.append(new String(bytes, 0, len, "utf-8"));
                    }

                    in.close();
                    JSONArray jsonArray = new JSONArray(str.toString());

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
                        URL url1 = new URL(Cache.MY_URL + "img/" + img_path);
                        InputStream in1 = url1.openStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(in1);
                        tips.setThumbnail(bitmap);
                        URL url2 = new URL(Cache.MY_URL + "img/" + head_img_path);
                        InputStream in2 = url2.openStream();
                        Bitmap bitmap1 = BitmapFactory.decodeStream(in2);
                        tips.setUserHead(bitmap1);

                        in1.close();
                        in2.close();
                        myPostList.add(tips);

                    }
                    Message message = new Message();
                    message.what = 3;
                    handler.sendMessage(message);
                    Log.e("Post", "initData");
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
        Log.e("Post", "setView");
        // arrayList.add(new Tips());
        recyclerView = findViewById(R.id.lv_article);
        smartRefreshLayout = findViewById(R.id.srl);
        toolbar = findViewById(R.id.post_toolbar);
        etContent = findViewById(R.id.search_content);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post.this.finish();
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    public void onClicked(View view) {
        if (myPostList != null) {
            if (etContent.getText().toString() != null) {
                if (etContent.getText().toString().length() != 0) {
                    ArrayList<Tips> arrayList = new ArrayList<>();
                    String str = etContent.getText().toString();
                    for (int i = 0; i < myPostList.size(); i++) {
                        if (myPostList.get(i).getTitle().contains(str)) {
                            arrayList.add(myPostList.get(i));
                        }
                    }
                    recyAdapter = new RecyAdapter(Post.this, arrayList);
                    recyclerView.setAdapter(recyAdapter);
                } else {
                    recyAdapter = new RecyAdapter(Post.this, myPostList);
                    recyclerView.setAdapter(recyAdapter);
                }

            }
        } else {
            Toast.makeText(Post.this, "快去发表帖子吧", Toast.LENGTH_LONG).show();
        }
    }
}