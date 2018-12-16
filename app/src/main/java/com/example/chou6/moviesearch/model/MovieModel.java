package com.example.chou6.moviesearch.model;
;
import android.util.Log;
import com.example.chou6.moviesearch.json.movie.MovieItem;

import java.io.Serializable;


public class MovieModel implements Serializable{
    String title;
    float rating;
    String director;
    String actor;
    String url;
    String year;
    String link;
    public MovieModel(MovieItem mi) {
        this.title = mi.title.replaceAll("<b>","").replaceAll("</b>","");
        this.rating = Float.parseFloat(mi.userRating)/2.0f;
        this.director = mi.director.replaceAll("<b>","").replaceAll("</b>","");
        this.actor = mi.actor.replaceAll("<b>","").replaceAll("</b>","");
        this.url = mi.image;
        this.year = mi.pubDate;
        this.link = mi.link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


}
