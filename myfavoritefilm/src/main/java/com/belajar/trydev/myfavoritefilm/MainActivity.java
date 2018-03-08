package com.belajar.trydev.myfavoritefilm;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.belajar.trydev.myfavoritefilm.Adapter.FavoriteFilmAdapter;
import com.belajar.trydev.myfavoritefilm.Database.DatabaseContract;
import com.belajar.trydev.myfavoritefilm.Database.DatabaseContract.*;

import static com.belajar.trydev.myfavoritefilm.Database.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener{

    private FavoriteFilmAdapter adapter;
    ListView lvFilm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("My Favorite Movie");
        lvFilm = (ListView) findViewById(R.id.lv_film);
        adapter = new FavoriteFilmAdapter(this, null, true);
        lvFilm.setAdapter(adapter);
        lvFilm.setOnItemClickListener(this);

        getSupportLoaderManager().initLoader(110,null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(110, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(110);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Cursor cursor = (Cursor) adapter.getItem(i);
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FilmColumns._ID));
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.setData(Uri.parse(CONTENT_URI+"/"+id));
        startActivity(intent);
    }
}
