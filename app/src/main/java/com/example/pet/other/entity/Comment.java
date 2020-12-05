package com.example.pet.other.entity;

import android.graphics.Bitmap;

public class Comment {
    private int id;
    private String Name;
    private String Time;
    private Bitmap Head;
    private String Content;
    private String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public Bitmap getHead() {
        return Head;
    }

    public void setHead(Bitmap head) {
        Head = head;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "OtherComment{" +
                "Name='" + Name + '\'' +
                ", Time='" + Time + '\'' +
                ", Head='" + Head + '\'' +
                ", Content='" + Content + '\'' +
                '}';
    }
}
