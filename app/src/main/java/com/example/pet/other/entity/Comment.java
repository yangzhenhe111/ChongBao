package com.example.pet.other.entity;

public class Comment {
    private String Name;
    private String Time;
    private int Head;
    private String Content;

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

    public int getHead() {
        return Head;
    }

    public void setHead(int head) {
        Head = head;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
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
