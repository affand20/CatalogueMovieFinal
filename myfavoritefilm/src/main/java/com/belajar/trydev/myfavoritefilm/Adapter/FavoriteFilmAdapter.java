package com.belajar.trydev.myfavoritefilm.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.belajar.trydev.myfavoritefilm.R;
import com.squareup.picasso.Picasso;

import static com.belajar.trydev.myfavoritefilm.Database.DatabaseContract.FilmColumns.DESKRIPSI;
import static com.belajar.trydev.myfavoritefilm.Database.DatabaseContract.FilmColumns.JUDUL;
import static com.belajar.trydev.myfavoritefilm.Database.DatabaseContract.FilmColumns.URL_POSTER;
import static com.belajar.trydev.myfavoritefilm.Database.DatabaseContract.getColumnString;

/**
 * Created by user on 3/7/2018.
 */

public class FavoriteFilmAdapter extends CursorAdapter {

    public FavoriteFilmAdapter(Context context, Cursor c, boolean autoRequery){
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie_fav, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor!=null){
            TextView tvJudul = (TextView) view.findViewById(R.id.judul);
            TextView tvOverview = (TextView) view.findViewById(R.id.desc);
            ImageView poster = (ImageView) view.findViewById(R.id.poster_movie);

            tvJudul.setText(getColumnString(cursor, JUDUL));
            tvOverview.setText(getColumnString(cursor, DESKRIPSI));
            String url = getColumnString(cursor, URL_POSTER);
            Picasso.with(context)
                    .load("https://image.tmdb.org/t/p/w300/"+url)
                    .into(poster);

        }
    }
}
