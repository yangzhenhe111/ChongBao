package com.example.pet.other.entity;

import java.io.Serializable;

public class Order implements Serializable {

    private int orderId;
    private String orderStart;
    private String orderEnd;
    private Pet pet;
    private String addresser;//收货人
    private String addressee;
    private String petShopContact;
    private String remarks;
    private String orderAmount;
    private String clientContact;
    private String runnerContact;
    private String runnerName;
    private String orderTime;
    private String kilometers;
    private String orderState;
    private int userId;
    private String addresseeContact;

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Order() {
    }



    public String getOrderStart() {
        return orderStart;
    }

    public void setOrderStart(String orderStart) {
        this.orderStart = orderStart;
    }

    public String getOrderEnd() {
        return orderEnd;
    }

    public void setOrderEnd(String orderEnd) {
        this.orderEnd = orderEnd;
    }



    public String getAddresser() {
        return addresser;
    }

    public void setAddresser(String addresser) {
        this.addresser = addresser;
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getPetShopContact() {
        return petShopContact;
    }

    public void setPetShopContact(String petShopContact) {
        this.petShopContact = petShopContact;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getClientContact() {
        return clientContact;
    }

    public void setClientContact(String clientContact) {
        this.clientContact = clientContact;
    }

    public String getRunnerContact() {
        return runnerContact;
    }

    public void setRunnerContact(String runnerContact) {
        this.runnerContact = runnerContact;
    }

    public String getRunnerName() {
        return runnerName;
    }

    public void setRunnerName(String runnerName) {
        this.runnerName = runnerName;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getKilometers() {
        return kilometers;
    }

    public void setKilometers(String kilometers) {
        this.kilometers = kilometers;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderStart='" + orderStart + '\'' +
                ", orderEnd='" + orderEnd + '\'' +
                ", pet=" + pet +
                ", addresser='" + addresser + '\'' +
                ", addressee='" + addressee + '\'' +
                ", petShopContact='" + petShopContact + '\'' +
                ", remarks='" + remarks + '\'' +
                ", orderAmount='" + orderAmount + '\'' +
                ", clientContact='" + clientContact + '\'' +
                ", runnerContact='" + runnerContact + '\'' +
                ", runnerName='" + runnerName + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", kilometers='" + kilometers + '\'' +
                ", orderState='" + orderState + '\'' +
                ", userId=" + userId +
                ", addresseeContact='" + addresseeContact + '\'' +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddresseeContact() {
        return addresseeContact;
    }

    public void setAddresseeContact(String addresseeContact) {
        this.addresseeContact = addresseeContact;
    }
}
