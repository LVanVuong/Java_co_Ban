package com.example.java_co_ban.Sign_Up.user;

public class User {
    private String taikhoan;

    @Override
    public String toString() {
        return "User{" +
                "taikhoan='" + taikhoan + '\'' +
                ", Matkhau='" + Matkhau + '\'' +
                "PhoneNumber+ " + PhoneNumbers + '\'' +
                '}';
    }
    private String PhoneNumbers;

    public String getPhoneNumbers() {
        return PhoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        PhoneNumbers = phoneNumbers;
    }

    public User(String phoneNumbers) {
        PhoneNumbers = phoneNumbers;
    }

    private String Matkhau;

    public User(String taikhoan, String matkhau , String PhoneNumbers) {
        this.taikhoan = taikhoan;
        this.Matkhau = matkhau;
        this.PhoneNumbers = PhoneNumbers;
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

}

