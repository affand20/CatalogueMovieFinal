package com.belajar.trydev.cataloguemovie;

import org.json.JSONObject;

/**
 * Created by user on 2/14/2018.
 */

public class Film {
    private String name, overview, date, photo, backdrop, vote;

    public Film(JSONObject object){
        try{
            this.name = object.getString("original_title");
            this.overview = object.getString("overview");
            this.vote = object.getString("vote_average");
            this.date = object.getString("release_date");
            this.photo = object.getString("poster_path");
            this.backdrop = object.getString("backdrop_path");

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
