package com.example.pet.other;

import android.app.Application;

import com.example.pet.other.entity.Order;
import com.example.pet.other.entity.Pet;
import com.example.pet.other.entity.Tips;
import com.example.pet.other.entity.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Cache extends Application {
    /**
     * 存储一些全局信息
     */
    public static User user /*= new User(2,"彭万","男",null,0,null,null,"12345678910",null,null)*/;
    public static String userPhone /*= "12345678910"*/;
    public static final String MY_URL = "http://10.7.90.171:8080/";//我的URL
    public static String url = "http://10.7.89.96:8080/ChongBao_war_exploded/";
    public static int user_id = 1;
    public static HashSet<Tips> myPostSet =new HashSet<>();//浏览历史数据
    public static HashSet<User> userHashSet = new HashSet<>();//用户列表
}
