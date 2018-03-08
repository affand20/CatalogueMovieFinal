package com.belajar.trydev.cataloguemovie.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.belajar.trydev.cataloguemovie.Database.DatabaseContract;
import com.belajar.trydev.cataloguemovie.Database.FavoriteFilmHelper;

import static com.belajar.trydev.cataloguemovie.Database.DatabaseContract.AUTHORITY;
import static com.belajar.trydev.cataloguemovie.Database.DatabaseContract.CONTENT_URI;

/**
 * Created by user on 3/6/2018.
 */

public class FilmProvider extends ContentProvider {

    private static final int FILM = 1;
    private static final int FILM_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.FAVORITE_FILM_ENG, FILM);
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.FAVORITE_FILM_ENG+"/#", FILM_ID);
    }

    private FavoriteFilmHelper favoriteFilmHelper;

    @Override
    public boolean onCreate() {
        favoriteFilmHelper = new FavoriteFilmHelper(getContext());
        favoriteFilmHelper.open();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case FILM:
                cursor = favoriteFilmHelper.queryProvider();
                break;
            case FILM_ID:
                cursor = favoriteFilmHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor!=null){
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long added;

        switch (sUriMatcher.match(uri)){
            case FILM:
                added = favoriteFilmHelper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }

        if (added>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI+"/"+added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)){
            case FILM_ID:
                deleted = favoriteFilmHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        if (deleted>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String s, String[] strings) {
        int updated;
        switch (sUriMatcher.match(uri)){
            case FILM_ID:
                updated = favoriteFilmHelper.updateProvider(uri.getLastPathSegment(), values);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}
