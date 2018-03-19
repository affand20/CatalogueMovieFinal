package com.belajar.trydev.cataloguemovie.Reminder;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.belajar.trydev.cataloguemovie.MainActivity;
import com.belajar.trydev.cataloguemovie.R;

/**
 * Created by user on 3/18/2018.
 */

public class NotificationDailyReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notification = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent comeback = new Intent(context, MainActivity.class);
        comeback.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, comeback, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_movies)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(context.getResources().getString(R.string.app_name)+" "+context.getResources().getString(R.string.daily_notif))
                .setVibrate(new long[]{500,500})
                .setSound(alarmSound)
                .setAutoCancel(true);

        notification.notify(100, builder.build());
    }
}
