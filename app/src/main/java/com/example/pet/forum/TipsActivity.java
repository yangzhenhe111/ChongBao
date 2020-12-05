package com.example.pet.forum;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pet.MainActivity;
import com.example.pet.R;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.Tips;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class TipsActivity extends AppCompatActivity {

    private ArrayList<Comment> arrayList = new ArrayList<>();
    private ListView listView;
    private LinearLayout btn_back;
    private TextView tv_landlordname;
    private TextView tv_time;
    private Button btn_topic;
    private TextView tv_title;
    private TextView tv_text;
    private TextView tv_likes;
    private TextView tv_comments;
    private TextView tv_forwards;
    private ImageView iv_pic;
    private ImageView iv_head;
    private Map<String,Object> maps = new HashMap<>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message message) {
            switch (message.what){
                case 1:
                    init();
                    break;
                case 2:
                    generateUI();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        getPost();
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
        tv_landlordname = findViewById(R.id.tv_landlordname);
        tv_time = findViewById(R.id.tv_time);
        btn_topic = findViewById(R.id.btn_t_topic);
        tv_title = findViewById(R.id.tips_title);
        tv_text = findViewById(R.id.tips_text);
        tv_likes = findViewById(R.id.tv_like);
        tv_comments = findViewById(R.id.tv_comment);
        tv_forwards = findViewById(R.id.tv_forward);
        iv_pic = findViewById(R.id.tips_images);
        iv_head = findViewById(R.id.img_head);
    }

    public void init(){
        Log.e("map", maps.toString());
        getImages(maps.get("img_path").toString());
        Log.e("bitmap",maps.get("img_path").toString());
        getHeadImages(maps.get("head_img_path").toString());
    }

    public void generateUI() {
        tv_landlordname.setText(maps.get("user_name").toString());
        tv_time.setText(maps.get("post_time").toString());
        btn_topic.setText(maps.get("topic").toString());
        tv_title.setText(maps.get("post_title").toString());
        tv_text.setText(maps.get("post_text").toString());
        tv_likes.setText(maps.get("count_likes").toString());
        tv_comments.setText(maps.get("count_comments").toString());
        tv_forwards.setText(maps.get("count_forwards").toString());
        iv_pic.setImageBitmap((Bitmap) maps.get("image"));
        iv_head.setImageBitmap((Bitmap) maps.get("head_image"));
        CommentAdapter commentAdapter = new CommentAdapter(this, arrayList, R.layout.comment_item);
        listView.setAdapter(commentAdapter);
    }

    public void getImages(final String path) {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(Cache.MY_URL + "GetImageByPath?path=" + path);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = urlConnection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    Log.e("bitmap", String.valueOf(bitmap));
                    maps.put("image",bitmap);
                    in.close();
                    handler.sendEmptyMessage(2);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public void getHeadImages(final String path) {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(Cache.MY_URL + "GetImageByPath?path=" + path);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = urlConnection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    Log.e("bitmap", String.valueOf(bitmap));
                    maps.put("head_image",bitmap);
                    in.close();
                    handler.sendEmptyMessage(2);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public void getPost(){
        new Thread(){
            @Override
            public void run() {
                String id = getIntent().getStringExtra("tipsid");
                Log.e("id", id + "");
                try {
                    URL url = new URL(Cache.MY_URL + "GetIdPostServlet?post_id=" + id);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream input = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                    StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line=bufferedReader.readLine())!=null){
                        stringBuffer.append(line);
                    }
                    JSONArray jsonArray = new JSONArray(stringBuffer.toString());
                    Log.e("123", stringBuffer.toString());
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String post_title = jsonObject.getString("post_title");
                    String post_time = jsonObject.getString("post_time");
                    String post_text = jsonObject.getString("post_text");
                    String topic = jsonObject.getString("post_topic");
                    String user_name = jsonObject.getString("user_name");
                    int count_likes = jsonObject.getInt("likes");
                    int count_comments = jsonObject.getInt("comments");
                    int count_forwards = jsonObject.getInt("forwards");
                    String img_path = jsonObject.getString("img_path");
                    String head_img_path = jsonObject.getString("user_picture_path");
                    Log.e("img_path", head_img_path);

                    maps.put("post_title",post_title);
                    maps.put("post_time",post_time);
                    maps.put("post_text",post_text);
                    maps.put("topic",topic);
                    maps.put("user_name",user_name);
                    maps.put("count_likes",count_likes);
                    maps.put("count_comments",count_comments);
                    maps.put("count_forwards",count_forwards);
                    maps.put("img_path",img_path);
                    maps.put("head_img_path",head_img_path);
                    Log.e("map0", maps.toString());

                    Message message = handler.obtainMessage();
                    message.what = 1;
                    handler.sendMessage(message);
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
}
