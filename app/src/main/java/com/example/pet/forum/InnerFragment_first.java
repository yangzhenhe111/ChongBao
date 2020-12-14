package com.example.pet.forum;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pet.R;
import com.example.pet.my.Login;
import com.example.pet.my.Post;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.Tips;
import com.rohit.recycleritemclicksupport.RecyclerItemClickSupport;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

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
import java.util.concurrent.CountDownLatch;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

public class InnerFragment_first extends Fragment {

    private ArrayList<Tips> tipsArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyAdapter recyAdapter;
    private Banner banner;
    private String title;
    private LinearLayout ll_progress;
    private int start = 0;
    private int end = 9;
    private int count = 0;

    public InnerFragment_first(String title) {
        this.title = title;
    }

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

    public InnerFragment_first() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getCount();
        View view = inflater.inflate(R.layout.fragment_inner_first, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);
        banner = view.findViewById(R.id.banner);
        getAllPost(tipsArrayList);
        initBanner();

        RecyclerItemClickSupport.addTo(recyclerView).setOnItemClickListener(new RecyclerItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                Intent intent = new Intent(getActivity(),New_post_detail.class);
                intent.putExtra("id",tipsArrayList.get(i).getId());
                Cache.myPostSet.add(tipsArrayList.get(i));
                startActivity(intent);
            }
        });
        return view;
    }
    public void getAllPost(ArrayList arrayList){
        new Thread(){
            @Override
            public void run() {
            try {
                URL url = new URL(Cache.MY_URL + "GetPostByTime");
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
                    int user_id = jsonObject.getInt("user_id");
                    Tips tips = new Tips();
                    tips.setId(post_id);
                    tips.setTitle(post_title);
                    tips.setTime(post_time);
                    tips.setLikes(count_likes);
                    tips.setComments(count_comments);
                    tips.setImagepath(img_path);
                    tips.setUserId(user_id);
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

    public void init(ArrayList arrayList){
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

    public void initAdapter(){
        recyAdapter = new RecyAdapter(getContext(),tipsArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyAdapter);
    }

    public void getCount(){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(Cache.MY_URL + "GetCountPostServlet");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream input = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                    StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line=bufferedReader.readLine())!=null){
                        stringBuffer.append(line);
                    }
                    Log.e("______________________", stringBuffer.toString());
                    JSONObject jsonObject = new JSONObject(stringBuffer.toString());
                    count = Integer.parseInt(jsonObject.getString("count"));
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

    public void initBanner(){
        ArrayList<Integer> images = new ArrayList<>();
        ArrayList<String> title = new ArrayList<>();
        title.add("猫咪的洗澡和驱虫");
        title.add("猫咪的日常用品");
        title.add("猫咪的食物");
        title.add("猫咪的日常护理");
        title.add("猫咪的常见病症");
        images.add(R.drawable.recommend1);
        images.add(R.drawable.recommend2);
        images.add(R.drawable.recommend3);
        images.add(R.drawable.recommend4);
        images.add(R.drawable.recommend5);

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setBannerTitles(title);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.start();
    }
}