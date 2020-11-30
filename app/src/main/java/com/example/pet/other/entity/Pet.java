package com.example.pet.other.entity;

public class Pet {
    private int petId;//宠物ID
    private String petName;//宠物名称
    private String petType;//宠物种类
    private int petAge;//宠物年龄
    private int petWeight;//宠物重量
    private String picturePath;//宠物头像

    public Pet() {
    }

    public Pet(int petId, String petName, String petType, int petAge, int petWeight, String picturePath) {
        this.petId = petId;
        this.petName = petName;
        this.petType = petType;
        this.petAge = petAge;
        this.petWeight = petWeight;
        this.picturePath = picturePath;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "petId=" + petId +
                ", petName='" + petName + '\'' +
                ", petType='" + petType + '\'' +
                ", petAge=" + petAge +
                ", petWeight=" + petWeight +
                ", picturePath='" + picturePath + '\'' +
                '}';
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
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

    public int getPetWeight() {
        return petWeight;
    }

    public void setPetWeight(int petWeight) {
        this.petWeight = petWeight;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
}
