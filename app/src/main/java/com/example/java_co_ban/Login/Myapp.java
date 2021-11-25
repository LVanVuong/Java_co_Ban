package com.example.java_co_ban.Login;

import android.app.Application;

import com.example.java_co_ban.data_local.DataLocal;

public class Myapp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocal.init(getApplicationContext());
    }
}
