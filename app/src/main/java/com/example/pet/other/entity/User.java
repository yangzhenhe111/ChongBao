package com.example.pet.other.entity;

import android.graphics.Bitmap;
import android.widget.ListView;

import java.util.Objects;

public class User {
    private int userId;//用户ID
    private String userName;//用户姓名
    private String userSex;//用户性别
    private String accountNumber;//账号
    private double accountAmount;//账户金额
    private String picturePath;//图片路径
    private String userPassword;//用户密码
    private String userPhone;//用户手机号
    private String userBrithday;//用户邮箱
    private String userAutograph;//个性签名
    private Bitmap photo;
    private String isFollow;

    public User() {
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public User(int userId, String userName, String userSex, String accountNumber, double accountAmount, String picturePath, String userPassword, String userPhone, String userBrithday, String userAutograph) {
        this.userId = userId;
        this.userName = userName;
        this.userSex = userSex;
        this.accountNumber = accountNumber;
        this.accountAmount = accountAmount;
        this.picturePath = picturePath;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.userBrithday = userBrithday;
        this.userAutograph = userAutograph;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userSex='" + userSex + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountAmount=" + accountAmount +
                ", picturePath='" + picturePath + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userBrithday='" + userBrithday + '\'' +
                ", userAutograph='" + userAutograph + '\'' +
                '}';
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
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

    public String getUserBrithday() {
        return userBrithday;
    }

    public void setUserBrithday(String userBrithday) {
        this.userBrithday = userBrithday;
    }

    public String getUserAutograph() {
        return userAutograph;
    }

    public void setUserAutograph(String userAutograph) {
        this.userAutograph = userAutograph;
    }

    public String getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(String isFollow) {
        this.isFollow = isFollow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                Double.compare(user.accountAmount, accountAmount) == 0 &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(userSex, user.userSex) &&
                Objects.equals(accountNumber, user.accountNumber) &&
                Objects.equals(picturePath, user.picturePath) &&
                Objects.equals(userPassword, user.userPassword) &&
                Objects.equals(userPhone, user.userPhone) &&
                Objects.equals(userBrithday, user.userBrithday) &&
                Objects.equals(userAutograph, user.userAutograph) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, userSex, accountNumber, accountAmount, picturePath, userPassword, userPhone, userBrithday, userAutograph);
    }
}
