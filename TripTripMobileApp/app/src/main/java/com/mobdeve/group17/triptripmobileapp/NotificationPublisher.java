package com.mobdeve.group17.triptripmobileapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mobdeve.group17.triptripmobileapp.utils.PreferenceUtils;

public class NotificationPublisher extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification_id";
    public static String END_LOCATION = "start location";
    public static String START_DATE = "start date";
    public static String NAME = "name";

    public void onReceive(Context context, Intent intent) {

        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        String name = intent.getStringExtra(NAME);
        String startDate = intent.getStringExtra(START_DATE);
        String endLoc = intent.getStringExtra(END_LOCATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyTrip")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Upcoming trip")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Hi " + name + "! You have an upcoming trip to " + endLoc + " on " + startDate))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(id, builder.build());

    }
}
