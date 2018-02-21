package com.belajar.trydev.cataloguemovie;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

/**
 * Created by user on 2/13/2018.
 */

public class NowPlayingFragment extends Fragment {

    static final String TAG = NowPlayingFragment.class.getSimpleName();
    public static final String EXTRAS = "extras";

    RecyclerView rv_now_playing;
    FilmAdapter list;
    ArrayList<Film> list_film = new ArrayList<>();

    private JSONObject myJson;

    ProgressDialog progress;
    TextView test;

    Bundle bundle;

    public NowPlayingFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_now_playing, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progress = new ProgressDialog(this.getActivity());
        rv_now_playing = (RecyclerView) view.findViewById(R.id.rv_now_playing);
        rv_now_playing.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rv_now_playing.setHasFixedSize(true);

        list = new FilmAdapter(this.getActivity());

        bundle = new Bundle();

        progress.setTitle(getResources().getString(R.string.app_name));
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setCancelable(false);
        progress.show();
        getListFilm();

        ItemClickSupport.addTo(rv_now_playing).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedFilm(list_film.get(position));
            }
        });
    }

    public void getListFilm(){
        String KEY = BuildConfig.MOVIEDB_API_KEY;
        String lang = "en-US";
        if (Locale.getDefault().getDisplayLanguage().toString().contains("English")){
            lang = "en-US";
        }
        if (Locale.getDefault().getDisplayLanguage().toString().contains("Indonesia")){
            lang = "id-ID";
        }
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+KEY+"&language="+lang;

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray getResult = responseObject.getJSONArray("results");
                    myJson = responseObject;

                    for (int i = 0; i < getResult.length(); i++) {
                        JSONObject json = getResult.getJSONObject(i);
                        Film film = new Film(json);
                        list_film.add(film);
                        Log.d(TAG, "list length: "+list_film.size());
                    }
                    list.setListFilm(list_film);
                    Log.d(TAG, "tes: "+list.getListFilm());
                    rv_now_playing.setAdapter(list);
                    progress.dismiss();//                    ItemClickSupport.addTo(rv_now_playing).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//                        @Override
//                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                            showSelectedFilm(list_film.get(position));
//                        }
//                    });
                } catch (Exception e){
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
