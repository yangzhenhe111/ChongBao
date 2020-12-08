package com.example.pet.forum;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pet.R;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.Tips;
import com.example.pet.other.entity.Topic;

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

public class TopicActivity extends AppCompatActivity {

    private LinearLayout btn_topic_back;
    private ListView topic_list;
    private ArrayList<Topic> arrayList = new ArrayList<>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message message) {
            switch (message.what){
                case 1:
                    Log.e("handler", "gethere----------------------");
                    init((ArrayList) message.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        getAllTopics();
        btn_topic_back = findViewById(R.id.btn_topic_back);
        btn_topic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TopicActivity.this,PublishActivity.class);
                startActivity(intent);
            }
        });
        topic_list = findViewById(R.id.lv_topic);
    }

    public void init(ArrayList arrayList){
        TopicAdapter topicAdapter = new TopicAdapter(this,arrayList,R.layout.topic_item);
        topic_list.setAdapter(topicAdapter);
    }

    public void getAllTopics(){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(Cache.MY_URL+"GetAllTopicsServlet");
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
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String topic_name = jsonObject.getString("topic_name");
                        int count = jsonObject.getInt("topic_count");
                        Topic topic = new Topic();
                        topic.setName(topic_name);
                        topic.setCount(String.valueOf(count));
                        arrayList.add(topic);
                    }
                    Message message = handler.obtainMessage();
                    message.what = 1;
                    message.obj = arrayList;
                    handler.sendMessage(message);
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
}
