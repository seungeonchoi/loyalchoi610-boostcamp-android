package com.example.chou6.moviesearch.json.movie;

public class MovieItem {
    public String title;
    public String link;
    public String image;
    public String subtitle;
    public String pubDate;
    public String director;
    public String actor;
    public String userRating;

    @Override
    public String toString() {
        return "MovieItem{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", image='" + image + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", director='" + director + '\'' +
                ", actor='" + actor + '\'' +
                ", userRating='" + userRating + '\'' +
                '}';
    }
}
