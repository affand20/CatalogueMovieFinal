package com.belajar.trydev.cataloguemovie;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by user on 2/14/2018.
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.CategoryViewHolder> {

    private Context context;
    ArrayList<Film> getListFilm(){
        return listFilm;
    }

    void setListFilm(ArrayList<Film> listFilm){
        this.listFilm = listFilm;
    }

    private ArrayList<Film> listFilm;

    FilmAdapter(Context context){
        this.context = context;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.tvTitle.setText(getListFilm().get(position).getName());
        holder.tvDate.setText(getListFilm().get(position).getDate());
        holder.tvOverview.setText(getListFilm().get(position).getOverview());

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w300/"+getListFilm().get(position).getPhoto())
                .crossFade()
                .into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return listFilm == null ? 0 : getListFilm().size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvDate;
        TextView tvOverview;
        ImageView imgPoster;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.judul);
            tvDate = (TextView) itemView.findViewById(R.id.time);
            tvOverview = (TextView) itemView.findViewById(R.id.desc);
            imgPoster = (ImageView) itemView.findViewById(R.id.poster_movie);
        }
    }

}
