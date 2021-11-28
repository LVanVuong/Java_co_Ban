package com.example.java_co_ban.LoginFrament;

import android.app.Application;

import com.example.java_co_ban.Sign_Up.DataLocal;

public class Myapp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocal.init(getApplicationContext());
    }
}
