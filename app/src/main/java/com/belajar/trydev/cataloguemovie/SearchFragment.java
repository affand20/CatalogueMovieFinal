package com.belajar.trydev.cataloguemovie;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

/**
 * Created by user on 2/17/2018.
 */

public class SearchFragment extends Fragment {
    public static String EXTRAS = "extras";
    String query = "";

    RecyclerView rv_search;
    FilmAdapter filmAdapter;

    ArrayList<Film> list_film;

    ProgressDialog progress;

    public SearchFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list_film = new ArrayList<>();

        query = getArguments().getString(EXTRAS);

        progress = new ProgressDialog(this.getActivity());
        progress.setTitle(getResources().getString(R.string.app_name));
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setCancelable(true);

        rv_search = (RecyclerView) view.findViewById(R.id.rv_search);
        rv_search.setHasFixedSize(true);
        rv_search.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        filmAdapter = new FilmAdapter(this.getActivity());

        ItemClickSupport.addTo(rv_search).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedFilm(list_film.get(position));
            }
        });


        progress.show();
        String KEY = BuildConfig.MOVIEDB_API_KEY;
        String lang = "en-US";
        if (Locale.getDefault().getDisplayLanguage().toString().contains("English")) {
            lang = "en-US";
        }
        if (Locale.getDefault().getDisplayLanguage().toString().contains("Indonesia")) {
            lang = "id-ID";
        }
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + KEY + "&language=" + lang + "&query=" + query;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray getResult = responseObject.getJSONArray("results");

                    for (int i = 0; i < getResult.length(); i++) {
                        JSONObject json = getResult.getJSONObject(i);
                        Film film = new Film(json);
                        list_film.add(film);
                    }

                    filmAdapter.setListFilm(list_film);
                    rv_search.setAdapter(filmAdapter);
                    progress.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

            private void showSelectedFilm(Film film){
        Bundle bundle = new Bundle();
        bundle.putString("title", film.getName());
        bundle.putString("year", film.getDate());
        bundle.putString("rating", film.getVote());
        bundle.putString("poster", film.getPhoto());
        bundle.putString("backdrop", film.getBackdrop());
        bundle.putString("description", film.getOverview());

        MainActivity.count = 2;

        Fragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, fragment)
                .commit();
    }
}
