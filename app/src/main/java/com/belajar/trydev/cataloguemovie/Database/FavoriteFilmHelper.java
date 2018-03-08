package com.belajar.trydev.cataloguemovie.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.belajar.trydev.cataloguemovie.Film;

import java.util.ArrayList;

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

public class FavoriteFilmHelper {

    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public FavoriteFilmHelper(Context context){
        this.context = context;
    }

    public FavoriteFilmHelper open() throws SQLException{
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<Film> getAllDataEng(){
        Cursor cursor = database.query(FAVORITE_FILM_ENG, null, null, null, null, null, _ID+" ASC", null);
        cursor.moveToFirst();
        ArrayList<Film> arrayList = new ArrayList<>();
        Film filmModel;
        if (cursor.getCount()>0){
            do{
                filmModel = new Film();
                filmModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                filmModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL)));
                filmModel.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESKRIPSI)));
                filmModel.setDate(cursor.getString(cursor.getColumnIndexOrThrow(TAHUN)));
                filmModel.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(URL_POSTER)));
                filmModel.setBackdrop(cursor.getString(cursor.getColumnIndexOrThrow(URL_BACKGROUND)));

                arrayList.add(filmModel);
                cursor.moveToNext();
            } while(!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public void insertTransactionEng(Film film){
        String sql = "INSERT INTO "+FAVORITE_FILM_ENG+" ("+JUDUL+", "+DESKRIPSI
                +", "+TAHUN+", "+URL_POSTER+", "+URL_BACKGROUND+") VALUES (?,?,?,?,?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, film.getName());
        stmt.bindString(2, film.getOverview());
        stmt.bindString(3, film.getDate());
        stmt.bindString(4, film.getPhoto());
        stmt.bindString(5, film.getBackdrop());
        stmt.execute();
        stmt.clearBindings();
    }

    public int updateEng(Film film){
        ContentValues args = new ContentValues();
        args.put(JUDUL, film.getName());
        args.put(DESKRIPSI, film.getOverview());
        args.put(TAHUN, film.getDate());
        args.put(URL_POSTER, film.getPhoto());
        args.put(URL_BACKGROUND, film.getBackdrop());
        return database.update(FAVORITE_FILM_ENG, args, _ID+"= '"+film.getId()+"'", null);
    }

    public int deleteEng(int id){
        return database.delete(FAVORITE_FILM_ENG, _ID+" = '"+id+"'", null);
    }

    public Cursor queryByIdProvider(String id){
        return database.query(FAVORITE_FILM_ENG, null,
                _ID+" = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public Cursor queryProvider(){
        return database.query(FAVORITE_FILM_ENG,
                null,
                null,
                null,
                null,
                null,
                _ID+" DESC");
    }

    public long insertProvider(ContentValues values){
        return database.insert(FAVORITE_FILM_ENG,null, values);
    }

    public int updateProvider(String id, ContentValues values){
        return database.update(FAVORITE_FILM_ENG, values, _ID+" =?", new String[]{id});
    }

    public int deleteProvider(String id){
        return database.delete(FAVORITE_FILM_ENG, _ID+" = ?", new String[]{id});
    }
}
