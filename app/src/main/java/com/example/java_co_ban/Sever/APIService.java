package com.example.java_co_ban.Sever;

public class APIService {

    private static String base_url = "https://emiliakute.000webhostapp.com/sever/";

    public static Dataservice getService(){
        return APIRetrofitClient.getClient(base_url).create(Dataservice.class);
    }
}
