package com.example.pet.other.entity;

public class Order {

    private int orderId;//订单ID
    private String orderStart;//订单起始地址
    private String orderEnd;//订单终点地址
    private String addresser;//饲主
    private String contact;//联系方式
    private String addressee;//医院联系人
    private String contact1;//联系方式
    private Pet petInfo;//宠物信息
    private String runnerName;//跑者姓名
    private String runnerPhone;//跑者电话
    private String orderTime;//下单时间
    private String kilometers;//公里数
    private int orderAmount;//订单金额
    private String remarks;//备注
    private String orderState;//订单状态

    public Order() {
    }

    public Order(int orderId, String orderStart, String orderEnd, String addresser, String contact, String addressee, String contact1, Pet petInfo, String runnerName, String runnerPhone, String orderTime, String kilometers, int orderAmount, String remarks, String orderState) {
        this.orderId = orderId;
        this.orderStart = orderStart;
        this.orderEnd = orderEnd;
        this.addresser = addresser;
        this.contact = contact;
        this.addressee = addressee;
        this.contact1 = contact1;
        this.petInfo = petInfo;
        this.runnerName = runnerName;
        this.runnerPhone = runnerPhone;
        this.orderTime = orderTime;
        this.kilometers = kilometers;
        this.orderAmount = orderAmount;
        this.remarks = remarks;
        this.orderState = orderState;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderStart='" + orderStart + '\'' +
                ", orderEnd='" + orderEnd + '\'' +
                ", addresser='" + addresser + '\'' +
                ", contact='" + contact + '\'' +
                ", addressee='" + addressee + '\'' +
                ", contact1='" + contact1 + '\'' +
                ", petInfo=" + petInfo +
                ", runnerName='" + runnerName + '\'' +
                ", runnerPhone='" + runnerPhone + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", kilometers='" + kilometers + '\'' +
                ", orderAmount=" + orderAmount +
                ", remarks='" + remarks + '\'' +
                ", orderState='" + orderState + '\'' +
                '}';
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getContact1() {
        return contact1;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public Pet getPetInfo() {
        return petInfo;
    }

    public void setPetInfo(Pet petInfo) {
        this.petInfo = petInfo;
    }

    public String getRunnerName() {
        return runnerName;
    }

    public void setRunnerName(String runnerName) {
        this.runnerName = runnerName;
    }

    public String getRunnerPhone() {
        return runnerPhone;
    }

    public void setRunnerPhone(String runnerPhone) {
        this.runnerPhone = runnerPhone;
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

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
}
