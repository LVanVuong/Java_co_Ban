package com.example.java_co_ban.Sign_Up;

import android.content.Context;
import android.util.Log;

import com.example.java_co_ban.Sign_Up.user.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataLocal {
    private static final String Pref = "Pref";
    private static final String KEY_LIST_USER = "key_list_user";
    private static final String TAG = "DataLocal";

    private static DataLocal mInstance;
    private MyShare myShare;

    public static void init(Context mContext) {
        getInstance().myShare = new MyShare(mContext);
    }

    public static DataLocal getInstance() {
        if (mInstance == null) {
            mInstance = new DataLocal();
        }
        return mInstance;
    }

    public static void setUser(User user) {
        List<User> users = getListUser();
        users.add(user);
        setListUser(users);
    }


    private static void setListUser(List<User> listuser) {
        Gson gson = new Gson();
        JsonArray jsonArray = gson.toJsonTree(listuser).getAsJsonArray();
        String JsonArray = jsonArray.toString();
        Log.i(TAG, JsonArray);
        DataLocal.getInstance().myShare.putString(KEY_LIST_USER, JsonArray);
    }

    public static List<User> getListUser() {
        String jsonUsers = DataLocal.getInstance().myShare.getString(KEY_LIST_USER);
        List<User> listUser = new ArrayList<>();
        try {
            Log.i(TAG, jsonUsers);
            JSONArray jsonArray = new JSONArray(jsonUsers);
            JSONObject jsonObject;
            User user;
            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                user = gson.fromJson(jsonObject.toString(), User.class);
                listUser.add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listUser;
    }


}
