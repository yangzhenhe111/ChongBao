package com.example.pet.other;

import com.example.pet.other.entity.Order;
import com.example.pet.other.entity.Pet;
import com.example.pet.other.entity.User;

import java.util.List;

public class Cache {
    /**
     * 存储一些全局信息
     */
public static  User user  ;
public static final String MY_URL="http://10.7.90.249:8080/ChongBaoService/";//我的URL
public static String url = "http://172.20.10.2:8080/ChongBao_war_exploded/";

public static List<Order> myOrderList ;//我的订单数据
public static List<Pet> myPetList;//我的宠物数据
}
