package com.example.java_co_ban.Notification;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.java_co_ban.PlayFilm.PlayFilmActivity;
import com.example.java_co_ban.R;
import com.example.java_co_ban.Data_Notification.NotificationData;

public class NotificationUtil extends Application {

    public static final String ID = "FilmApp";

    public static void showNotification(Context context, NotificationData notiData) {

        // Chi hien thi notification khi notiData co thong tin
        if (notiData != null) {
            Intent intent = new Intent(context, PlayFilmActivity.class);
            intent.putExtra("url_phim", notiData.getUrlFilm());

            PendingIntent pIntent = PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.backgroud);
            Notification notification = new NotificationCompat.Builder(context, ID)
                    .setChannelId(createNotificationChannel(context))
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setSmallIcon(R.drawable.ic_noti)
                    .setContentTitle(notiData.getTitle())
                    .setContentText(notiData.getContent())
                    .setLargeIcon(bitmap)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(notiData.getTitleFull())
                            .setBigContentTitle(notiData.getContentFull()))
                    .setContentIntent(pIntent)
                    .build();
            NotificationManagerCompat notificationCompat = NotificationManagerCompat.from(context.getApplicationContext());
            notificationCompat.notify(1, notification);
        }
    }

    private static String createNotificationChannel(Context context) {
        String channelId = "Chanel Film";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(ID, channelId, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Description");
            channel.enableVibration(true);
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
        return ID;
    }
}
