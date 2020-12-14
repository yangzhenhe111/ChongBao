package com.example.pet.chat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pet.R;
import com.example.pet.other.Cache;
import com.example.pet.other.entity.User;

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
import java.util.concurrent.CountDownLatch;

public class FollowMe extends Fragment {

    private String title;
    private ArrayList<com.example.pet.other.entity.User> userArrayList = new ArrayList<>();
    private ListView lv_followme;
    private View v;

    public FollowMe(String title){
        this.title = title;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message message) {
            switch (message.what){
                case 1:
                    init((ArrayList) message.obj);
                    Log.e("111111111111111","000000000000000000");
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followme, container, false);
        v = view;
        getAllMyFollow();
        lv_followme = view.findViewById(R.id.lv_followme);
        TextView tv_no_follow = view.findViewById(R.id.tv_no_follow);
        if (userArrayList == null){
            tv_no_follow.setText("您还没有关注其他人快去关注一下吧!");
        }
        return view;
    }

    public void getAllMyFollow(){
        new Thread(){
            @Override
            public void run() {
                try {
                    int user_id = 2;
                    URL url = new URL("http://192.168.43.202:8080/ChongBaoService_war_exploded/GetAllFollowMeServlet?user_id="+user_id);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream input = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                    StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line=bufferedReader.readLine())!=null){
                        stringBuffer.append(line);
                    }
                    Log.e("asdasdasdasdasd",stringBuffer.toString());
                    JSONArray jsonArray = new JSONArray(stringBuffer.toString());
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("user_id");
                        String name = jsonObject.getString("user_name");
                        String path = jsonObject.getString("user_picture_path");
                        String autograph = jsonObject.getString("user_autograph");
                        String isFollow = jsonObject.getString("isFollow");
                        com.example.pet.other.entity.User user = new com.example.pet.other.entity.User();
                        user.setUserId(id);
                        user.setUserName(name);
                        user.setPicturePath(path);
                        user.setUserAutograph(autograph);
                        user.setIsFollow(isFollow);
                        userArrayList.add(user);
                    }
                    Message message = handler.obtainMessage();
                    message.what = 1;
                    message.obj = userArrayList;
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
        Log.e("222222222222222","000000000000000000");
        TextView tv_no_follow = v.findViewById(R.id.tv_no_follow);
        if (arrayList != null && arrayList.size() > 0) {
            tv_no_follow.setVisibility(View.GONE);
        }
        getImages(arrayList);
    }


    public void getImages(final ArrayList<com.example.pet.other.entity.User> arrayList) {
        final CountDownLatch latch = new CountDownLatch(arrayList.size());
        Log.e("333333333333333333","000000000000000000");
        for (int i = 0; i < arrayList.size(); i ++ ) {
            final User user = arrayList.get(i);
            new Thread(){
                @Override
                public void run() {
                    try {
                        String path = user.getPicturePath();
                        URL url = new URL(Cache.MY_URL + "GetImageByPath?path=" + path);
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        InputStream in = urlConnection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(in);
                        user.setPhoto(bitmap);
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

    private void initAdapter() {
        FollowMeAdapter followMeAdapter = new FollowMeAdapter(getContext(),userArrayList,R.layout.my_follow_item);
        lv_followme.setAdapter(followMeAdapter);
        Log.e("44444444444444444","000000000000000000");
    }
}
