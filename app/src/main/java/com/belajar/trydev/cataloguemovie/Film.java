package com.belajar.trydev.cataloguemovie;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.belajar.trydev.cataloguemovie.Database.DatabaseContract;

import org.json.JSONObject;

import static android.provider.BaseColumns._ID;
import static com.belajar.trydev.cataloguemovie.Database.DatabaseContract.getColumnInt;
import static com.belajar.trydev.cataloguemovie.Database.DatabaseContract.getColumnString;

/**
 * Created by user on 2/14/2018.
 */

public class Film implements Parcelable {
    private String name, overview, date, photo, backdrop, vote;
    private int id;

    public Film(JSONObject object){
        try{
            this.name = object.getString("original_title");
            this.overview = object.getString("overview");
            this.vote = object.getString("vote_average");
            this.date = object.getString("release_date");
            this.photo = object.getString("poster_path");
            this.backdrop = object.getString("backdrop_path");

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Film(){}

    public Film(String judul, String overview, String tahun, String poster, String background){

    }

    public Film(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.name = getColumnString(cursor, DatabaseContract.FilmColumns.JUDUL);
        this.overview = getColumnString(cursor, DatabaseContract.FilmColumns.DESKRIPSI);
        this.date = getColumnString(cursor, DatabaseContract.FilmColumns.TAHUN);
        this.photo = getColumnString(cursor, DatabaseContract.FilmColumns.URL_POSTER);
        this.backdrop = getColumnString(cursor, DatabaseContract.FilmColumns.URL_BACKGROUND);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.overview);
        dest.writeString(this.date);
        dest.writeString(this.photo);
        dest.writeString(this.backdrop);
        dest.writeString(this.vote);
        dest.writeInt(this.id);
    }

    protected Film(Parcel in) {
        this.name = in.readString();
        this.overview = in.readString();
        this.date = in.readString();
        this.photo = in.readString();
        this.backdrop = in.readString();
        this.vote = in.readString();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<Film> CREATOR = new Parcelable.Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel source) {
            return new Film(source);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
}
