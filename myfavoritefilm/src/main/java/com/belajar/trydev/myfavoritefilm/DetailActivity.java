package com.belajar.trydev.myfavoritefilm;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.belajar.trydev.myfavoritefilm.Model.Film;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity{

    ImageView backdrop, poster;
    TextView title, tahun, description;

    private Film film = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        backdrop = (ImageView) findViewById(R.id.image_backdrop);
        poster = (ImageView) findViewById(R.id.image_poster);
        title = (TextView) findViewById(R.id.title_detail);
        tahun = (TextView) findViewById(R.id.tahun);
        description = (TextView) findViewById(R.id.description);

        Uri uri = getIntent().getData();

        if (uri!=null){
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            if (cursor != null){
                if (cursor.moveToFirst()) film = new Film(cursor);
                cursor.close();
//                getSupportActionBar().setTitle();
            }
        }

        if (film!=null){
            title.setText(film.getName());
            description.setText(film.getOverview());
            tahun.setText(film.getDate().substring(0,4));

            Picasso.with(this)
                    .load("https://image.tmdb.org/t/p/w780/"+film.getPhoto())
                    .into(poster);

            Picasso.with(this)
                    .load("https://image.tmdb.org/t/p/w780/"+film.getBackdrop())
                    .into(backdrop);
        }


    }
}
