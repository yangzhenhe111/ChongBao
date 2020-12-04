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
public static final String MY_URL="http://10.7.90.249:8080/ChongBaoService";
public static String url = "http://172.20.10.2:8080/ChongBaoService_war_exploded/";
//172.20.10.2
public static List<Order> myOrderList ;
public static List<Pet> myPetList;
}
