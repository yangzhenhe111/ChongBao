package com.example.pet.other.entity;

import android.graphics.Bitmap;

import java.util.Objects;

public class Tips {
    private int id;
    private int userId;
    private String userName;
    private Bitmap userHead;
    private String time;
    private String topic;
    private String title;
    private String text;
    private Bitmap thumbnail;
    private int likes;
    private int comments;
    private int forwards;
    private String imagepath;
    private String headImagepath;

    public String getHeadImagepath() {
        return headImagepath;
    }

    public void setHeadImagepath(String headImagepath) {
        this.headImagepath = headImagepath;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
//    public Tips(int id, int userId, String userName, Bitmap userHead, String time, String topic, String title, String text, Bitmap thumbnail, int likes, int comments, int forwards) {
//        this.id = id;
//        this.userId = userId;
//        this.userName = userName;
//        this.userHead = userHead;
//        this.time = time;
//        this.topic = topic;
//        this.title = title;
//        this.text = text;
//        this.thumbnail = thumbnail;
//        this.likes = likes;
//        this.comments = comments;
//        this.forwards = forwards;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Bitmap getUserHead() {
        return userHead;
    }

    public void setUserHead(Bitmap userHead) {
        this.userHead = userHead;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getForwards() {
        return forwards;
    }

    public void setForwards(int forwards) {
        this.forwards = forwards;
    }

    @Override
    public String toString() {
        return "Tips{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +

                ", time='" + time + '\'' +
                ", topic='" + topic + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +

                ", likes=" + likes +
                ", comments=" + comments +
                ", forwards=" + forwards +
                ", imagepath='" + imagepath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tips tips = (Tips) o;
        return id == tips.id &&
                userId == tips.userId &&
                likes == tips.likes &&
                comments == tips.comments &&
                forwards == tips.forwards &&
                Objects.equals(userName, tips.userName) &&
                Objects.equals(time, tips.time) &&
                Objects.equals(topic, tips.topic) &&
                Objects.equals(title, tips.title) &&
                Objects.equals(text, tips.text) &&
                Objects.equals(imagepath, tips.imagepath) &&
                Objects.equals(headImagepath, tips.headImagepath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, userName, userHead, time, topic, title, text, thumbnail, likes, comments, forwards, imagepath, headImagepath);
    }
}