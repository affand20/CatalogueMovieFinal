package com.belajar.trydev.cataloguemovie.Adapter;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.belajar.trydev.cataloguemovie.Database.FavoriteFilmHelper;
import com.belajar.trydev.cataloguemovie.Film;
import com.belajar.trydev.cataloguemovie.R;
import com.bumptech.glide.Glide;

/**
 * Created by user on 2/14/2018.
 */

public class FilmAdapterCursor extends RecyclerView.Adapter<FilmAdapterCursor.CategoryViewHolder> {

    private Context context;

    FavoriteFilmHelper favoriteFilmHelper;

    public void setListFilm(Cursor listFilm){
        this.listFilm = listFilm;
    }

    private Cursor listFilm;

    public FilmAdapterCursor(Context context){
        this.context = context;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_fav, parent, false);
        return new CategoryViewHolder(itemRow);
    }

    public Film getItem(int position){
        if (!listFilm.moveToPosition(position)){
            throw new IllegalStateException("Position Invalid");
        }
        return new Film(listFilm);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, final int position) {
        final Film film = getItem(position);
        holder.tvTitle.setText(film.getName());
        holder.tvOverview.setText(film.getOverview());

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w300/"+film.getPhoto())
                .crossFade()
                .into(holder.imgPoster);

    }

    @Override
    public int getItemCount() {
        if (listFilm==null) return 0;
        return listFilm.getCount();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        CardView cvItem;
        TextView tvOverview;
        ImageView imgPoster;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.judul);
            cvItem = (CardView) itemView.findViewById(R.id.card_item_film);
            tvOverview = (TextView) itemView.findViewById(R.id.desc);
            imgPoster = (ImageView) itemView.findViewById(R.id.poster_movie);
        }
    }

}
