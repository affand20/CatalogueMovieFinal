package com.belajar.trydev.cataloguemovie;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import static com.belajar.trydev.cataloguemovie.Database.DatabaseContract.CONTENT_URI;
import static com.belajar.trydev.cataloguemovie.Database.DatabaseContract.FAVORITE_FILM_ENG;

/**
 * Created by user on 3/6/2018.
 */

public class FavoriteFragment extends Fragment {
    static final String TAG = FavoriteFragment.class.getSimpleName();
    public static final String EXTRAS = "extras";

    RecyclerView rv_favorite;
    private Cursor list;
    private FilmAdapterCursor adapter;
    Bundle bundle;

    public FavoriteFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rv_favorite = (RecyclerView) view.findViewById(R.id.rv_list_favoritfilm);
        rv_favorite.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rv_favorite.setHasFixedSize(true);

        adapter = new FilmAdapterCursor(this.getActivity());
        adapter.setListFilm(list);
        rv_favorite.setAdapter(adapter);

        ItemClickSupport.addTo(rv_favorite).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Film film = adapter.getItem(position);
                showSelectedFilm(film);
            }
        });

        new LoadData().execute();
    }

    private void showSelectedFilm(Film film){
        Bundle bundle = new Bundle();
        bundle.putString("title", film.getName());
        bundle.putString("year", film.getDate());
        bundle.putString("rating", film.getVote());
        bundle.putString("poster", film.getPhoto());
        bundle.putString("backdrop", film.getBackdrop());
        bundle.putString("description", film.getOverview());

        MainActivity.count = 4;

        Fragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, fragment)
                .commit();
    }

    class LoadData extends AsyncTask<Void, Void, Cursor>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getActivity().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            list = cursor;
            adapter.setListFilm(list);
            adapter.notifyDataSetChanged();

            if (list.getCount()==0){
                Toast.makeText(getActivity(), "Tidak ada data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
