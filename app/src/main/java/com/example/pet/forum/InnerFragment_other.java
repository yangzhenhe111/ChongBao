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
import android.widget.Toast;

import com.example.pet.R;
import com.example.pet.my.My;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class InnerFragment_other extends Fragment {

    private ArrayList<Tips> tipsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyAdapter recyAdapter;
    private String title;

    public InnerFragment_other(String title) {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inner_other, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        getAllPost(tipsList);
        init(tipsList);

        RecyclerItemClickSupport.addTo(recyclerView).setOnItemClickListener(new RecyclerItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                Intent intent = new Intent(getActivity(),New_post_detail.class);
                intent.putExtra("id",tipsList.get(i).getId());
                startActivity(intent);
            }
        });

        return view;
    }

    public void init(ArrayList arrayList){
        getImages(arrayList);
    }

    public void initAdapter(){
        recyAdapter = new RecyAdapter(getContext(),tipsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyAdapter);
    }

    public void getAllPost(ArrayList arrayList){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(Cache.MY_URL + "GetAllSortServlet?topic="+title);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream input = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                    StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line=bufferedReader.readLine())!=null){
                        stringBuffer.append(line);
                    }
                    tipsList.clear();
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
                        tipsList.add(tips);
                    }
                    Message message = handler.obtainMessage();
                    message.what = 1;
                    message.obj = tipsList;
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

}