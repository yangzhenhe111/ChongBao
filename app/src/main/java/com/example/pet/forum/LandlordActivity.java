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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet.MainActivity;
import com.example.pet.R;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.Tips;
import com.rohit.recycleritemclicksupport.RecyclerItemClickSupport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class LandlordActivity extends AppCompatActivity {

    private ArrayList<Tips> tipsArrayList = new ArrayList<>();
    private ImageView btn_back;
    private TextView tv_name;
    private ListView lv_landlord;
    private RoundImageView iv_head;
    private Button btn_follow;
    private Map<String,Object> maps = new HashMap<>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message message) {
            switch (message.what){
                case 1:
                    Log.e("handler", "gethere----------------------");
                    init((ArrayList) message.obj);
                    break;
                case 2:
                    Toast.makeText(LandlordActivity.this,"关注成功!",Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(LandlordActivity.this,"关注失败!",Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(LandlordActivity.this,"取关成功!",Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(LandlordActivity.this,"取关失败!",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord);
//        getHeadImages(getIntent().getStringExtra("head_path"));
        getAllTips(getIntent().getStringExtra("user_id"));
        btn_back = findViewById(R.id.btn_landlord_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LandlordActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tv_name = findViewById(R.id.landlordname);
        tv_name.setText(getIntent().getStringExtra("name"));
        iv_head = findViewById(R.id.iv_head);
        iv_head.setImageResource(R.drawable.kobe);
//        iv_head.setImageBitmap((Bitmap) maps.get("head_image"));
        btn_follow = findViewById(R.id.btn_follow);
        btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn_follow.getText().equals("＋关注")){
                    btn_follow.setText("☰已关注");
                    btn_follow.setBackgroundColor(getResources().getColor(R.color.followGray));
                    addFollow();
                }else{
                    btn_follow.setText("＋关注");
                    btn_follow.setBackgroundColor(getResources().getColor(R.color.followBlue));
                    deleteFollow();
                }
            }
        });
        lv_landlord = findViewById(R.id.lv_landlord);
    }

    public void init(ArrayList arrayList){
        Log.e("init方法", "----------------------------");
        getImages(arrayList);
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

    public void initAdapter() {
        Log.e("initAdapter方法", "----------------------------");
        LandlordTipsAdapter landlordTipsAdapter = new LandlordTipsAdapter(this,tipsArrayList,R.layout.landlord_tips_item);
        lv_landlord.setAdapter(landlordTipsAdapter);
    }

    public void getAllTips(String id){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(Cache.MY_URL + "GetPostByIDServlet?id="+id);
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
                        int post_id = jsonObject.getInt("post_id");
                        String post_title = jsonObject.getString("post_title");
                        String post_time = jsonObject.getString("post_time");
                        int count_likes = jsonObject.getInt("likes");
                        int count_comments = jsonObject.getInt("comments");
                        String img_path = jsonObject.getString("picture_path");
                        Tips tips = new Tips();
                        tips.setId(post_id);
                        tips.setTitle(post_title);
                        tips.setTime(post_time);
                        tips.setLikes(count_likes);
                        tips.setComments(count_comments);
                        tips.setImagepath(img_path);
                        tipsArrayList.add(tips);
                    }
                    Message message = handler.obtainMessage();
                    message.what = 1;
                    message.obj = tipsArrayList;
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

    public void addFollow(){
        new Thread(){
            @Override
            public void run() {
                try {
                    int follow_id = Integer.parseInt(getIntent().getStringExtra("user_id"));
                    int user_id = 1;
                    URL url = new URL(Cache.MY_URL + "AddFollowServlet?user_id="+user_id+"&follow_id="+follow_id);
                    InputStream inputStream = url.openStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
                    String isPublish = bufferedReader.readLine();
                    if (isPublish.equals("true")){
                        Message message = handler.obtainMessage();
                        message.what = 2;
                        handler.sendMessage(message);
                    }else{
                        Message message = handler.obtainMessage();
                        message.what = 3;
                        handler.sendMessage(message);
                    }
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

    public void deleteFollow(){
        new Thread(){
            @Override
            public void run() {
                try {
                    int follow_id = Integer.parseInt(getIntent().getStringExtra("user_id"));
                    int user_id = 1;
                    URL url = new URL(Cache.MY_URL + "DeleteFollowServlet?user_id="+user_id+"&follow_id="+follow_id);
                    InputStream inputStream = url.openStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
                    String isPublish = bufferedReader.readLine();
                    if (isPublish.equals("true")){
                        Message message = handler.obtainMessage();
                        message.what = 4;
                        handler.sendMessage(message);
                    }else{
                        Message message = handler.obtainMessage();
                        message.what = 5;
                        handler.sendMessage(message);
                    }
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