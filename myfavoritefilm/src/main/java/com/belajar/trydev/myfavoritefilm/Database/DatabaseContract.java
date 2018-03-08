package com.belajar.trydev.myfavoritefilm.Database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by user on 3/5/2018.
 */

public class DatabaseContract {
    public static String FAVORITE_FILM_ENG = "favorite_film_eng";

    public static final class FilmColumns implements BaseColumns{
        public static String JUDUL = "judul";
        public static String DESKRIPSI = "deskripsi";
        public static String TAHUN = "tahun";
        public static String URL_POSTER = "poster";
        public static String URL_BACKGROUND = "background";
    }

    public static final String AUTHORITY = "com.belajar.trydev.cataloguemovie";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(FAVORITE_FILM_ENG)
            .build();

    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
