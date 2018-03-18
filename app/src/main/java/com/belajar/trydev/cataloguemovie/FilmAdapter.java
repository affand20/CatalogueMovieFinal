package com.belajar.trydev.cataloguemovie;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.belajar.trydev.cataloguemovie.Database.FavoriteFilmHelper;
import com.belajar.trydev.cataloguemovie.Prefs.AppPreference;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by user on 2/14/2018.
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.CategoryViewHolder> {

    private Context context;

    FavoriteFilmHelper favoriteFilmHelper;
    AppPreference appPreference;

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
    public void onBindViewHolder(CategoryViewHolder holder, final int position) {
        holder.tvTitle.setText(getListFilm().get(position).getName());
//        holder.tvDate.setText(getListFilm().get(position).getDate());
        holder.tvOverview.setText(getListFilm().get(position).getOverview());
        holder.btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteFilmHelper = new FavoriteFilmHelper(context);
//                appPreference = new AppPreference(context);


                Film film = getListFilm().get(position);

                favoriteFilmHelper.open();

                favoriteFilmHelper.beginTransaction();
                if (!favoriteFilmHelper.checkData(film.getName())){
                    try{
                        favoriteFilmHelper.insertTransactionEng(film);
                        favoriteFilmHelper.setTransactionSuccess();
                        Toast.makeText(context, context.getResources().getString(R.string.toast), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, FavoriteFilmWidget.class);
                        i.setAction(FavoriteFilmWidget.UPDATE_WIDGET);
                        context.sendBroadcast(i);
                    } catch (Exception e){
                        String TAG = "TAG";
                        Log.e(TAG, "onClick: Exception");
                    }
                } else{
                    Toast.makeText(context, context.getResources().getString(R.string.available), Toast.LENGTH_SHORT).show();
                }
                favoriteFilmHelper.endTransaction();

                favoriteFilmHelper.close();
            }
        });

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
        Button btn_fav;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.judul);
            tvDate = (TextView) itemView.findViewById(R.id.time);
            tvOverview = (TextView) itemView.findViewById(R.id.desc);
            imgPoster = (ImageView) itemView.findViewById(R.id.poster_movie);
            btn_fav = (Button) itemView.findViewById(R.id.btn_fav);
        }
    }

}
