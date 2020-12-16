package com.example.pet.forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet.R;
import com.example.pet.other.Cache;
import com.example.pet.other.MainActivity;
import com.example.pet.other.entity.Comment;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class New_post_detail extends Activity {

    private ListView listView;
    private TextView tv_landlordname;
    private TextView tv_time;
    private TextView btn_topic;
    private TextView tv_title;
    private TextView tv_text;
    private TextView tv_likes;
    private TextView tv_comments;
    private ImageView iv_pic;
    private ImageView iv_head;
    private EditText enter_comment;
    private Button publish_comment;
    private TextView tv_forwards;
    private LinearLayout btn_back;
    private Map<String,Object> maps = new HashMap<>();
    private ArrayList<Comment> arrayList = new ArrayList<>();
    private int id;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ScrollView scrollView;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message message) {
            switch (message.what){
                case 1:
                    init();
                    break;
                case 2:
                    getHeadImages((String) maps.get("head_img_path"));
                    break;
                case 3:
                    initimg((ArrayList)message.obj);
                case 4:
                    Toast.makeText(New_post_detail.this,"上传成功",Toast.LENGTH_LONG);
                    break;
                case 5:
                    Toast.makeText(New_post_detail.this,"上传失败",Toast.LENGTH_LONG);
                    break;
                case 6:
                    generateUI();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post_detail);

        id = getIntent().getIntExtra("id",0);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        scrollView = findViewById(R.id.scroll);

        mSwipeRefreshLayout.setRefreshing(true);
        //被刷新时的操作
        getPost(id);
        getAllComments(id);

        //更新UI
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //更新成功后设置UI，停止更新
                generateUI();
                initAdapter();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        },1000);
        handleDownPullUpdate();

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                mSwipeRefreshLayout.setEnabled(scrollView.getScrollY()==0);
            }
        });

        btn_back = findViewById(R.id.tips_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(New_post_detail.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        listView = findViewById(R.id.lv_comment);
        tv_landlordname = findViewById(R.id.push_name);
        tv_time = findViewById(R.id.push_time);
        btn_topic = findViewById(R.id.sort);
        tv_title = findViewById(R.id.main_title);
        tv_likes = findViewById(R.id.no_like);
        tv_text = findViewById(R.id.content_text);
        tv_comments = findViewById(R.id.no_comment);
        iv_pic = findViewById(R.id.content_img);
        iv_head = findViewById(R.id.head);
        iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(New_post_detail.this,LandlordActivity.class);
                startActivity(intent);
            }
        });
        enter_comment = findViewById(R.id.enter_comment);
        publish_comment = findViewById(R.id.publish_comment);
        tv_forwards = findViewById(R.id.no_forward);
        publish_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishComment(id);
                refresh();
            }
        });
    }

    public void handleDownPullUpdate() {
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                //被刷新时的操作
                getPost(id);
                getAllComments(id);
                //更新UI
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //更新成功后设置UI，停止更新
                        generateUI();
                        initAdapter();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                },1000);
            }
        });
    }

    public void init(){
        // object类型不能toString，需要强转
        getImages((String) maps.get("img_path"));
    }

    public void initimg(ArrayList arrayList){
        getCommentHeadImages(arrayList);
    }

    public void initAdapter(){
        CommentAdapter commentAdapter = new CommentAdapter(this, arrayList, R.layout.comment_item);
        listView.setAdapter(commentAdapter);
    }

    public void generateUI() {
        tv_landlordname.setText((String)maps.get("username"));
        tv_time.setText((String)maps.get("post_time"));
        btn_topic.setText((String)maps.get("topic"));
        tv_title.setText((String)maps.get("post_title"));
        tv_text.setText((String)maps.get("post_text"));
        String countLikes = (String) maps.get("count_likes");
        tv_likes.setText(countLikes);
        tv_comments.setText((String)maps.get("count_comments"));
        tv_forwards.setText((String)maps.get("count_forwards"));
        iv_pic.setImageBitmap((Bitmap) maps.get("image"));
        iv_head.setImageBitmap((Bitmap) maps.get("head_image"));
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
                    maps.put("image", bitmap);
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
                    maps.put("head_image",bitmap);
                    in.close();
                    handler.sendEmptyMessage(6);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public void getPost(final int id){
        new Thread(){
            @Override
            public void run() {
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

                    maps.put("post_title",post_title);
                    maps.put("post_time",post_time);
                    maps.put("post_text",post_text);
                    maps.put("topic",topic);
                    maps.put("username",user_name);
                    maps.put("count_likes",count_likes+"");
                    maps.put("count_comments",count_comments+"");
                    maps.put("count_forwards",count_forwards+"");
                    maps.put("img_path",img_path);
                    maps.put("head_img_path",head_img_path);

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

    public void getAllComments(final int tip_id){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(Cache.MY_URL + "GetAllComment?post_id="+tip_id);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream input = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                    StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line=bufferedReader.readLine())!=null){
                        stringBuffer.append(line);
                    }
                    arrayList.clear();
                    JSONArray jsonArray = new JSONArray(stringBuffer.toString());
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("comment_id");
                        String name = jsonObject.getString("user_name");
                        String time = jsonObject.getString("comment_time");
                        String comment_text = jsonObject.getString("comments");
                        String img_path = jsonObject.getString("user_picture_path");
                        Comment comment = new Comment();
                        comment.setId(id);
                        comment.setName(name);
                        comment.setTime(time);
                        comment.setContent(comment_text);
                        comment.setPath(img_path);
                        arrayList.add(comment);
                        Message message = handler.obtainMessage();
                        message.what = 3;
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

    public void getCommentHeadImages(final ArrayList<Comment> arrayList) {
        final CountDownLatch latch = new CountDownLatch(arrayList.size());

        for (int i = 0; i < arrayList.size(); i ++ ) {
            final Comment comment = arrayList.get(i);
            new Thread(){
                @Override
                public void run() {
                    try {
                        String path = comment.getPath();
                        URL url = new URL(Cache.MY_URL + "GetImageByPath?path=" + path);
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        InputStream in = urlConnection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(in);
                        comment.setHead(bitmap);
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
//            initAdapter();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void publishComment(int postid){
        new Thread(){
            @Override
            public void run() {
                try {
                    String text = enter_comment.getText().toString();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                    Date date = new Date(System.currentTimeMillis());
                    String time = simpleDateFormat.format(date);
                    int userid = Cache.user_id;
                    URL url = new URL(Cache.MY_URL + "PostComment?comment="+text+"&comment_time="+time+"&post_id="+postid+"&user_id="+userid);
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
    private void refresh() {
        onCreate(null);
    }

    public void linforward(View view) {
        shareQQ(this,"宠宝："+maps.get("post_title").toString());
    }
    public static void shareQQ(Context mContext, String content) {
        if (PlatformUtil.isInstallApp(mContext,PlatformUtil.PACKAGE_MOBILE_QQ)) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
            intent.putExtra(Intent.EXTRA_TEXT, content);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity"));
            mContext.startActivity(Intent.createChooser(intent, "Share"));
        } else {
            Toast.makeText(mContext, "您需要安装QQ客户端", Toast.LENGTH_LONG).show();
        }
    }
}