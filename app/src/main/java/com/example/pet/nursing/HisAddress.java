package com.example.pet.nursing;

public class HisAddress {
    private String add;
    private String name;
    private String tel;
    private int id;

    public HisAddress(String add, String name, String tel, int id){
        this.add = add;
        this.name = name;
        this.tel = tel;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
