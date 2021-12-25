package com.example.java_co_ban.Sign_Up.user;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String taikhoan;
    private String PhoneNumbers;
    private String Matkhau;
    private String email;
    private String fullname;
    private String date;
    private String sex;
    private int id;

    public User(String email, String fullname, String date, String sex,String taikhoan, String phoneNumbers) {
        this.taikhoan = taikhoan;
        this.PhoneNumbers = phoneNumbers;
        this.email = email;
        this.fullname = fullname;
        this.date = date;
        this.sex = sex;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "taikhoan='" + taikhoan + '\'' +
                ", Matkhau='" + Matkhau + '\'' +
                "PhoneNumber+ " + PhoneNumbers + '\'' +
                '}';
    }

    public String getPhoneNumbers() {
        return PhoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        PhoneNumbers = phoneNumbers;
    }

    public User(String phoneNumbers) {
        PhoneNumbers = phoneNumbers;
    }



    public User(String taikhoan, String matkhau , String PhoneNumbers) {
        this.taikhoan = taikhoan;
        this.Matkhau = matkhau;
        this.PhoneNumbers = PhoneNumbers;
    }
    public User(){

    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return Matkhau;
    }

    public void setMatkhau(String matkhau) {
        Matkhau = matkhau;
    }


    public Map<String,Object> tomap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("email",email);
        result.put("fullname",fullname);
        result.put("date",date);
        result.put("sex",sex);
        result.put("taikhoan",taikhoan);
        result.put("phonenumbers",PhoneNumbers);

        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

