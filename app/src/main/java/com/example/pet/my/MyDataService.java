package com.example.pet.my;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.pet.other.Cache;
import com.example.pet.other.entity.Order;
import  com.example.pet.other.entity.Pet;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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
        if(Cache.myPetList == null && Cache.user !=null){
            try{
                Cache.myPetList = new ArrayList<>();
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
                   Pet pet= new Pet();
                    pet.setPetId(rs.getInt("petId"));
                    pet.setPicturePath(rs.getString("picturePath"));
                    pet.setPetName(rs.getString("petName"));
                    pet.setPetType(rs.getString("petType"));
                    pet.setPetAge(rs.getInt("petAge"));
                    pet.setPetWeight(rs.getString("petWeight"));
                    pet.setUserId(rs.getInt("userId"));
                    Cache.myPetList.add(pet);
                }
            }catch(IOException | JSONException e) {
                e.printStackTrace();
            }
        }

        //订单数据下载
        if (Cache.myOrderList == null && Cache.user !=null) {
            try {
                Cache.myOrderList = new ArrayList<Order>();
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
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject rs = jsonArray.getJSONObject(i);
                    Order order = new Order();
                    order.setOrderId(rs.getInt("orderId"));
                    order.setOrderStart(rs.getString("orderStart"));
                    order.setOrderEnd(rs.getString("orderEnd"));
                    int id = rs.getInt("petId");
                    for(int j=0;j<Cache.myPetList.size();j++){
                        if(Cache.myPetList.get(j).getPetId() == id){
                            order.setPet(Cache.myPetList.get(j));
                            break;
                        }
                    }
                    //设置
                    order.setAddresser(rs.getString("addresser"));
                    order.setAddresseeContact(rs.getString("addressee"));
                    order.setPetShopContact(rs.getString("petShopContact"));
                    order.setRemarks(rs.getString("remarks"));
                    order.setOrderAmount(rs.getString("orderAmount"));
                    order.setClientContact(rs.getString("clientContact"));
                    order.setRunnerContact(rs.getString("runnerContact"));
                    order.setRunnerName(rs.getString("runnerName"));
                    order.setOrderTime(rs.getString("orderTime"));
                    order.setKilometers(rs.getString("kilometers"));
                    order.setOrderState(rs.getString("orderState"));
                    order.setUserId(rs.getInt("userId"));
                    order.setAddresseeContact(rs.getString("addresseeContact"));
                    Cache.myOrderList.add(order);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
