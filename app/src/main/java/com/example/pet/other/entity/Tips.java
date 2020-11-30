package com.example.pet.other.entity;

import android.graphics.Bitmap;

public class Tips {
    private int id;
    private Bitmap head;
    private String name;
    private String time;
    private String topic;
    private String title;
    private Bitmap thumbnail;
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getHead() {
        return head;
    }

    public void setHead(Bitmap head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Tips{" +
                "id=" + id +
                ", head=" + head +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", topic='" + topic + '\'' +
                ", title='" + title + '\'' +
                ", thumbnail=" + thumbnail +
                ", text='" + text + '\'' +
                '}';
    }
}
