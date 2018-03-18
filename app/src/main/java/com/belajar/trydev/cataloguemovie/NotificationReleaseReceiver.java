package com.belajar.trydev.cataloguemovie;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

/**
 * Created by user on 3/18/2018.
 */

public class NotificationReleaseReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        final String filmname = "";

        String KEY = BuildConfig.MOVIEDB_API_KEY;
        String lang = "en-US";
        if (Locale.getDefault().getDisplayLanguage().toString().contains("English")){
            lang = "en-US";
        }
        if (Locale.getDefault().getDisplayLanguage().toString().contains("Indonesia")){
            lang = "id-ID";
        }
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key="+KEY+"&language="+lang;

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String (responseBody);
                    JSONObject response = new JSONObject(result);
                    JSONArray getResult = response.getJSONArray("results");

                    for (int i = 0; i < getResult.length(); i++) {
                        JSONObject json = getResult.getJSONObject(i);
                        Film film = new Film(json);
                        Log.d("RELEASE", "onSuccess: "+film.getDate());
                        if (film.getDate().equals(date)){
                            filmname.concat(film.getName()+", ");
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        String text = String.format(context.getResources().getString(R.string.release_today),filmname);
        if (!filmname.equals("")){
            showNotification(text, context);
        }
    }

    private void showNotification(String content, Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pending = PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pending)
                .setSmallIcon(R.drawable.ic_movies)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(content)
                .setVibrate(new long[]{500,500})
                .setSound(alarmSound)
                .setAutoCancel(true);

        notificationManager.notify(100, builder.build());
    }
}
