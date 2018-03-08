package com.belajar.trydev.cataloguemovie.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.belajar.trydev.cataloguemovie.Database.DatabaseContract.FAVORITE_FILM_ENG;
import static com.belajar.trydev.cataloguemovie.Database.DatabaseContract.FilmColumns.DESKRIPSI;
import static com.belajar.trydev.cataloguemovie.Database.DatabaseContract.FilmColumns.JUDUL;
import static com.belajar.trydev.cataloguemovie.Database.DatabaseContract.FilmColumns.TAHUN;
import static com.belajar.trydev.cataloguemovie.Database.DatabaseContract.FilmColumns.URL_BACKGROUND;
import static com.belajar.trydev.cataloguemovie.Database.DatabaseContract.FilmColumns.URL_POSTER;


/**
 * Created by user on 3/5/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "filmfavorit";

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_FAV_ENG = "create table "+FAVORITE_FILM_ENG+
            " ("+_ID+" integer primary key autoincrement, " +
            JUDUL+" text not null, " +
            DESKRIPSI+" text not null, " +
            TAHUN+" text not null, "+
            URL_POSTER+" text not null, " +
            URL_BACKGROUND+" text not null);";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAV_ENG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+FAVORITE_FILM_ENG);
        onCreate(db);
    }
}
