package com.example.pet.my;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.pet.other.Cache;
import com.example.pet.other.entity.Order;
import  com.example.pet.other.entity.Pet;
import com.example.pet.other.entity.Tips;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MyDataService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyDataService(String name) {
        super(name);
    }

    public MyDataService() {
        super("MyDataService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //宠物数据下载

            try{
                if(Cache.myPetList!=null){
                    Cache.myPetList.clear();
                }else{
                    Cache.myPetList = new ArrayList<>();
                }

                URL url = new URL(Cache.MY_URL+"MyPet?userId="+Cache.user.getUserId());
                InputStream in = url.openStream();
                StringBuilder str = new StringBuilder();
                byte[] bytes = new byte[256];
                int len =0;
                while ((len=in.read(bytes))!=-1){
                    str.append(new String(bytes,0,len,"utf-8"));
                }

                in.close();
                JSONArray jsonArray = new JSONArray(str.toString());

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject rs = jsonArray.getJSONObject(i);
                    Pet pet= new Gson().fromJson(rs.toString(),Pet.class);
                    Log.e("pet",pet.toString());
//                    pet.setPetId(rs.getInt("petId"));
                    String path =rs.getString("picturePath");
                    URL url1 = new URL(Cache.MY_URL +"img/" +path);
                    Log.e("url",url1.toString());
                    InputStream in1 = url1.openStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(in1);
                    pet.setPicture(bitmap);
//                    pet.setPetName(rs.getString("petName"));
//                    pet.setPetType(rs.getString("petType"));
//                    pet.setPetAge(rs.getInt("petAge"));
//                    pet.setPetWeight(rs.getString("petWeight"));
//                    pet.setUserId(rs.getInt("userId"));
//                    pet.setPetAutograph(rs.getString("petAutograph"));
                    Log.e("MyDataService",pet.toString());
                    Cache.myPetList.add(pet);
                    Log.e("pet","next");
                }

            }catch(IOException | JSONException e) {
                e.printStackTrace();
            }

            try {
                if(Cache.myOrderList!=null){
                    Cache.myOrderList.clear();
                }else{
                    Cache.myOrderList = new ArrayList<>();
                }
                URL url  = new URL(Cache.MY_URL +"MyOrder?userId="+Cache.user.getUserId());
                InputStream in  = url.openStream();
                StringBuilder str= new StringBuilder();
                byte[] bytes = new byte[256];
                int len =0;
                while ((len = in.read(bytes))!=-1){
                    str.append(new String(bytes,0,len,"utf-8"));

                }

                in.close();
                JSONArray jsonArray = new JSONArray(str.toString());
                Log.e("",jsonArray.toString());
                Cache.myOrderList = new ArrayList();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject rs = jsonArray.getJSONObject(i);
                    Order order = new Gson().fromJson(rs.toString(),Order.class);
//                    order.setOrderId(rs.getInt("orderId"));
//                    order.setOrderStart(rs.getString("orderStart"));
//                    order.setOrderEnd(rs.getString("orderEnd"));
                    int id = rs.getInt("petId");
                    for(int j=0;j<Cache.myPetList.size();j++){
                        if(Cache.myPetList.get(j).getPetId() == id){
                            order.setPet(Cache.myPetList.get(j));
                            Log.e("MyDataService",id+ "gggg");

                        }
                    }
                    //设置
//                    order.setAddresser(rs.getString("addresser"));
//                    order.setAddressee(rs.getString("addressee"));
//                    order.setPetShopContact(rs.getString("petShopContact"));
//                    order.setRemarks(rs.getString("remarks"));
//                    order.setOrderAmount(rs.getString("orderAmount"));
//                    order.setClientContact(rs.getString("clientContact"));
//                    order.setRunnerContact(rs.getString("runnerContact"));
//                    order.setRunnerName(rs.getString("runnerName"));
//                    order.setOrderTime(rs.getString("orderTime"));
//                    order.setKilometers(rs.getString("kilometers"));
//                    order.setOrderState(rs.getString("orderState"));
//                    order.setUserId(rs.getInt("userId"));
//                    order.setAddresseeContact(rs.getString("addresseeContact"));
                    Log.e("MyDataService",order.toString());
                    Cache.myOrderList.add(order);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

        try {
            if(Cache.myPostList!=null){
                Cache.myPostList.clear();
            }else{
                Cache.myPostList = new ArrayList<>();
            }
            URL url = new URL(Cache.MY_URL+"MyTip?userId="+ Cache.user.getUserId());
            InputStream in  = url.openStream();
            StringBuilder str= new StringBuilder();
            byte[] bytes = new byte[256];
            int len =0;
            while ((len = in.read(bytes))!=-1){
                str.append(new String(bytes,0,len,"utf-8"));
            }

            in.close();
            JSONArray jsonArray = new JSONArray(str.toString());
            Log.e("Post",jsonArray.toString());
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
                URL url1 = new URL(Cache.MY_URL +"img/" +img_path);
                InputStream in1 = url1.openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in1);
                tips.setThumbnail(bitmap);
                URL url2 = new URL(Cache.MY_URL +"img/"+head_img_path);
                InputStream in2 = url2.openStream();
                Bitmap bitmap1 = BitmapFactory.decodeStream(in2);
                tips.setUserHead(bitmap1);
                in1.close();
                in2.close();
                Cache.myPostList.add(tips);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
