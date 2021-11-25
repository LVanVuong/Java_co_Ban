package com.example.java_co_ban.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.java_co_ban.Notification.NotificationUtil;
import com.example.java_co_ban.PlayFilm.PlayFilmActivity;
import com.example.java_co_ban.R;
import com.example.java_co_ban.Data_Notification.NotificationData;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG = MyFirebaseMessagingService.class.getName();

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        RemoteMessage.Notification notification = remoteMessage.getNotification();

//        if(notification == null){
//            return;
//        }
//        String strTitle   = notification.getTitle();
//        String strMessage = notification.getBody();

        // dataMessage
        Map<String, String > stringMap = remoteMessage.getData();
        if(stringMap == null){
            return;
        }
        String strTitle = stringMap.get("title");
        String strMessage = stringMap.get("body");
        String urlFilm = stringMap.get("url_film");

        NotificationData notiData = new NotificationData();
        notiData.setTitle(strTitle);
        notiData.setContentFull(strMessage);
        notiData.setTitleFull(strTitle);
        notiData.setUrlFilm(urlFilm); // only for testing
        NotificationUtil.showNotification(this, notiData);
       //sendNotification(strTitle,strMessage);
    }

    private void sendNotification(String strTitle,String strMessage) {

        Intent intent = new Intent(this , PlayFilmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this , 0,
                intent,PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NotificationUtil.ID)
                .setSmallIcon(R.drawable.ic_noti)
                .setContentTitle(strTitle)
                .setContentText(strMessage)
                .setContentIntent(pendingIntent);

        Notification notification = notificationBuilder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(notification != null){
            notificationManager.notify(1,notification);
        }

    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.e(TAG,token);
        //token : e-Rrj5O1Sm2kPoa5sl4Yxs:APA91bHt9_Fo5NGXWSyl2W3YiGwPIdWYtWeZoQXjNu4Y5NNGjyX2O0queUui48evdAbKh81D3kAiiJPTrBrITS1LwOfDjAuzWG4aKZ1bXpCf_GQ7qKyFVcvtqZpGi1ge9jr4-xhdJVWX
    }
}
