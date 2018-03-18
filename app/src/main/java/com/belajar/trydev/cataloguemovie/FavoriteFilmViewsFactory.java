package com.belajar.trydev.cataloguemovie;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.belajar.trydev.cataloguemovie.Database.FavoriteFilmHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by user on 3/14/2018.
 */

public class FavoriteFilmViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    public static ArrayList<Film> listFilm = new ArrayList<>();
    private List<Bitmap> mWidgetItems = new ArrayList<>();
    private Context context;
    private int mAppWidgetId;

    public FavoriteFilmViewsFactory(Context context, Intent intent){
        this.context = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    private void getData(Context context){
        FavoriteFilmHelper helper = new FavoriteFilmHelper(context);
        helper.open();
        listFilm = helper.getAllDataEng();
        helper.close();
    }

    @Override
    public void onCreate() {
        getData(context);
    }

    @Override
    public void onDataSetChanged() {
        getData(context);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Log.d("LIST FILM", "getCount: "+listFilm.size());
        return listFilm.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
//        rv.setImageViewBitmap(R.id.imageView, mWidgetItems.get(position));

        Bitmap bmp = null;

        try{
            bmp = Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w300/"+listFilm.get(position).getPhoto())
                    .asBitmap()
                    .error(new ColorDrawable(context.getResources().getColor(R.color.colorPrimary)))
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        } catch (InterruptedException | ExecutionException e){
            Toast.makeText(context, "Error saat load gambar :(", Toast.LENGTH_SHORT).show();
            Log.d("WIDGET LOAD ERROR", "getViewAt: error!!");
        }

        rv.setImageViewBitmap(R.id.imageView, bmp);

        Bundle extras = new Bundle();
        extras.putInt(FavoriteFilmWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
