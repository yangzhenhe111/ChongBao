package com.example.pet.chat;


/**
 * Created by dell on 2018/6/3.
 * Created by qiyueqing on 2018/6/3.
 */

public class Book {
    private String name;
    private int imageId;

    public Book(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
