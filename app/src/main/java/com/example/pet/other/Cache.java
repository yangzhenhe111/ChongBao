package com.example.pet.other;

import com.example.pet.other.entity.Order;
import com.example.pet.other.entity.Pet;
import com.example.pet.other.entity.Tips;
import com.example.pet.other.entity.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Cache {
    /**
     * 存储一些全局信息
     */
    public static User user;
    public static String userPhone;
    public static final String MY_URL = "http://192.168.43.202:8080/ChongBaoService_war_exploded/";//我的URL
    public static String url = "http://10.7.89.96:8080/ChongBao_war_exploded/";
    public static int user_id = 1;
    public static List<Order> myOrderList;//我的订单数据
    public static List<Pet> myPetList;//我的宠物数据
    public static ArrayList<Tips> myPostList;//我的帖子数据
    public static HashSet<User> userHashSet = new HashSet<>();//用户列表
}
