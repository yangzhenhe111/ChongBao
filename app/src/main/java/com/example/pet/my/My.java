package com.example.pet.my;

public class My {
    private int arrow,img;
    private String text;

    public int getImg() {
        return img;
    }
    public String getText() {
        return text;
    }

    public int getArrow() {
        return arrow;
    }

    public My(int img, String text, int arrow){
        this.img=img;
        this.arrow=arrow;
        this.text=text;
    }
}
