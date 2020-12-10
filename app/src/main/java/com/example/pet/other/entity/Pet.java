package com.example.pet.other.entity;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Pet implements Serializable {
    private int  petId;
    private Bitmap picture;
    private String petName;
    private String petType;
    private int petAge;
    private String petWeight;
    private int userId;
    private String petAutograph;
    private String picturePath;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getPetAutograph() {
        return petAutograph;
    }

    public void setPetAutograph(String petAutograph) {
        this.petAutograph = petAutograph;
    }

    public Pet() {
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public int getPetAge() {
        return petAge;
    }

    public void setPetAge(int petAge) {
        this.petAge = petAge;
    }

    public String getPetWeight() {
        return petWeight;
    }

    public void setPetWeight(String petWeight) {
        this.petWeight = petWeight;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "Pet{" +
                "petId=" + petId +
                ", petName='" + petName + '\'' +
                ", petType='" + petType + '\'' +
                ", petAge=" + petAge +
                ", petWeight='" + petWeight + '\'' +
                ", userId=" + userId +
                ", petAutograph='" + petAutograph + '\'' +
                ", picturePath='" + picturePath + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
