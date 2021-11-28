package com.example.java_co_ban.Sign_Up;

import android.content.Context;
import android.content.SharedPreferences;

public class MyShare {
    static final String SHARE_PREF = "Share_Pref";

    SharedPreferences mSharedPreferences;

    public MyShare(Context mContext) {
        mSharedPreferences = mContext.getSharedPreferences(SHARE_PREF, Context.MODE_PRIVATE);
    }

    public void putString(String key, String Value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, Value);
        editor.apply();
    }

    public String getString(String Key) {
        return mSharedPreferences.getString(Key, "");
    }
}
