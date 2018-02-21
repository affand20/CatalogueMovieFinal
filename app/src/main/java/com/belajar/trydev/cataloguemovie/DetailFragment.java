package com.belajar.trydev.cataloguemovie;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by user on 2/15/2018.
 */

public class DetailFragment extends Fragment {

    ImageView backdrop, poster;
    TextView year, title, rating, description;
    String URL_BACKGROUND;
    String URL_POSTER;
    String YEAR;
    String TITLE;
    String RATING;
    String DESCRIPTION;

    public DetailFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backdrop = (ImageView) view.findViewById(R.id.image_backdrop);
        poster = (ImageView) view.findViewById(R.id.image_poster);
        year = (TextView) view.findViewById(R.id.year);
        title = (TextView) view.findViewById(R.id.title_detail);
//        rating = (TextView) view.findViewById(R.id.rating);
        description = (TextView) view.findViewById(R.id.description);

        URL_BACKGROUND = getArguments().getString("backdrop");
        URL_POSTER = getArguments().getString("poster");
        TITLE = getArguments().getString("title");
        YEAR = getArguments().getString("year");
        RATING = getArguments().getString("rating");
        DESCRIPTION = getArguments().getString("description");

        description.setText(DESCRIPTION);
//        if (!URL_BACKGROUND.equals("")){
            title.setText(TITLE);
//            rating.setText(RATING);
            description.setText(DESCRIPTION);
            year.setText(YEAR.substring(0,4));
            Glide.with(getActivity().getApplicationContext())
                    .load("https://image.tmdb.org/t/p/w780/"+URL_BACKGROUND)
                    .crossFade()
                    .into(backdrop);
//        }
        Glide.with(getActivity().getApplicationContext())
                .load("https://image.tmdb.org/t/p/w300/"+URL_POSTER)
                .crossFade()
                .into(poster);
    }
}
