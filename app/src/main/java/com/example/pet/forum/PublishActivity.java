package com.example.pet.forum;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet.MainActivity;
import com.example.pet.R;

import com.example.pet.other.Cache;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PublishActivity extends AppCompatActivity {


    private Button btn_back;
    private Button btn_publish;
    private EditText et_title;
    private EditText et_text;
    private RelativeLayout add_image;
    private TextView tv_add_topic;
    private Button btn_topic;
    private ImageView upload;
    private Button oneeee;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message message) {
            switch (message.what){
                case 1:
                    Toast.makeText(PublishActivity.this,"发布成功",Toast.LENGTH_LONG);
                    break;
                case 2:
                    Toast.makeText(PublishActivity.this,"发布失败",Toast.LENGTH_LONG);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        btn_back = findViewById(R.id.btn_publish_back);
        oneeee = findViewById(R.id.one);
        oneeee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
                publishPost();
                Intent intent = new Intent();
                intent.setClass(PublishActivity.this,MainActivity.class);
                startActivity(intent);
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
        tv_add_topic = findViewById(R.id.btn_add_topic);
        tv_add_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PublishActivity.this,TopicActivity.class);
                startActivity(intent);
            }
        });
        btn_topic = findViewById(R.id.btn_topic);
        btn_topic.setText(getIntent().getStringExtra("topic"));
    }

    public void publishPost(){
        new Thread(){
            @Override
            public void run() {
                int userid = 1;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                String time = simpleDateFormat.format(date);
                String title = et_title.getText().toString();
                String text = et_text.getText().toString();
                int number_like = 0;
                int number_comment = 0;
                int number_forward = 0;
                String topic = getIntent().getStringExtra("topic");
                try {
                    URL url = new URL(Cache.MY_URL+"AddPostServlet?user_id="+userid+"&post_time="+time+"&post_topic="+topic+"&post_title="+title+"&post_text="+text+"&number_like="+number_like+"&number_comment="+number_comment+"&number_forward="+number_forward);
                    InputStream inputStream = url.openStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
                    String isPublish = bufferedReader.readLine();
                    Log.e("返回的东西", isPublish);
                    if (isPublish.equals("true")){
                        Message message = handler.obtainMessage();
                        message.what = 1;
                        handler.sendMessage(message);
                    }else{
                        Message message = handler.obtainMessage();
                        message.what = 2;
                        handler.sendMessage(message);
                    }
                    bufferedReader.close();
                    inputStream.close();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
