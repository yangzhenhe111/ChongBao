package com.example.pet.other.entity;

public class User {
    private int userId;//用户ID
    private String userName;//用户姓名
    private String accountNumber;//账号
    private double accountAmount;//账户金额
    private String picturePath;//图片路径
    private String userPassword;//用户密码
    private String userPhone;//用户手机号
    private String userEmail;//用户邮箱
    private String userAutograph;//个性签名

    public User() {
    }

    public User(int userId, String userName, String accountNumber, double accountAmount, String picturePath, String userPassword, String userPhone, String userEmail, String userAutograph) {
        this.userId = userId;
        this.userName = userName;
        this.accountNumber = accountNumber;
        this.accountAmount = accountAmount;
        this.picturePath = picturePath;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userAutograph = userAutograph;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountAmount=" + accountAmount +
                ", picturePath='" + picturePath + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userAutograph='" + userAutograph + '\'' +
                '}';
    }

    public double getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(double accountAmount) {
        this.accountAmount = accountAmount;
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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAutograph() {
        return userAutograph;
    }

    public void setUserAutograph(String userAutograph) {
        this.userAutograph = userAutograph;
    }
}
