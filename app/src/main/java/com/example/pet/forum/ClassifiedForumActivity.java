package com.example.pet.forum;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pet.R;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.Tips;

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
import java.util.concurrent.CountDownLatch;

public class ClassifiedForumActivity extends AppCompatActivity {

    private EditText et_classified_search;
    private Button btn_classified_search;
    private Button btn_classified_publish;
    private TextView tv_classified_newest;
    private TextView tv_classified_hot;
    private TextView tv_classified_essence;
    private ListView lv_tips;
    private ArrayList<Tips> arrayList = new ArrayList<>();
    private ArrayList<Tips> tipsArrayList;
    private String classified;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message message) {
            switch (message.what){
                case 1:
                    init((ArrayList) message.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classified_forum);
        getClassifiedTips();
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


    }

    public void initAdapter(){
        ClassfiedForumAdapter classfiedForumAdapter = new ClassfiedForumAdapter(this,arrayList,R.layout.forum_tips_item);
        lv_tips.setAdapter(classfiedForumAdapter);
    }

    public void init(ArrayList arrayList){
        Log.e("init方法", "----------------------------");
        getImages(arrayList);
        getHeadImages(arrayList);
    }

    public void getImages(final ArrayList<Tips> arrayList) {
        final CountDownLatch latch = new CountDownLatch(arrayList.size());

        for (int i = 0; i < arrayList.size(); i ++ ) {
            final Tips tips = arrayList.get(i);
            new Thread(){
                @Override
                public void run() {
                    try {
                        String path = tips.getImagepath();
                        URL url = new URL(Cache.MY_URL + "GetImageByPath?path=" + path);
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        InputStream in = urlConnection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(in);
                        tips.setThumbnail(bitmap);
                        in.close();
                        latch.countDown();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }
        try {
            latch.await();
            initAdapter();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void getHeadImages(final ArrayList<Tips> arrayList) {
        final CountDownLatch latch = new CountDownLatch(arrayList.size());

        for (int i = 0; i < arrayList.size(); i ++ ) {
            final Tips tips = arrayList.get(i);
            new Thread(){
                @Override
                public void run() {
                    try {
                        String path = tips.getHeadImagepath();
                        URL url = new URL(Cache.MY_URL + "GetImageByPath?path=" + path);
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        InputStream in = urlConnection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(in);
                        tips.setUserHead(bitmap);
                        in.close();
                        latch.countDown();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }
        try {
            latch.await();
            initAdapter();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void getClassifiedTips(){
        new Thread(){
            @Override
            public void run() {
                try {
                    classified = getIntent().getStringExtra("classified");
                    Log.e("classified", classified);
                    URL url = new URL(Cache.url + "GetAllSortServlet?sortname="+classified);
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
                    Log.e("arrayList", stringBuffer.toString());
                    tipsArrayList = new ArrayList<>();
                    for (int i=0;i<jsonArray.length();i++){
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

                        Log.e("post_id", post_id+"");

                        Message message = handler.obtainMessage();
                        message.what = 1;
                        message.obj = arrayList;
                        handler.sendMessage(message);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
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
